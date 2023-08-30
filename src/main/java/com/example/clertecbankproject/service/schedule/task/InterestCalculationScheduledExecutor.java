package com.example.clertecbankproject.service.schedule.task;

import com.example.clertecbankproject.config.LoadPropertiesService;
import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Status;
import com.example.clertecbankproject.model.entity.Transaction;
import com.example.clertecbankproject.model.entity.TransactionType;
import com.example.clertecbankproject.model.entity.util.InterestDate;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.model.repository.InterestDateRepository;
import com.example.clertecbankproject.service.PaymentCheckService;
import com.example.clertecbankproject.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestCalculationScheduledExecutor implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(InterestCalculationScheduledExecutor.class);
    private static final String INTEREST_RATE_PATH = "interest_monthly_to_balance";
    private InterestDateRepository interestDateRepository;
    private AccountRepository accountRepository;
    private TransactionService transactionService;
    private PaymentCheckService paymentCheckService;

    public InterestCalculationScheduledExecutor(InterestDateRepository interestDateRepository, AccountRepository accountRepository, TransactionService transactionService, PaymentCheckService paymentCheckService) {
        this.interestDateRepository = interestDateRepository;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.paymentCheckService = paymentCheckService;
    }

    @Override
    public synchronized void run() {
        BigDecimal interestRate = LoadPropertiesService.getInterestRate(INTEREST_RATE_PATH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<InterestDate> interestDates = new ArrayList<>();
        try {
            interestDates =interestDateRepository.getAllInterestDate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String>interestDatesFormattedToString = new ArrayList<>();
        interestDatesFormattedToString = interestDates.stream()
                .map(InterestDate::getInterestDateOfCalculation)
                .map(e ->e.format(formatter))
                .collect(Collectors.toList());
        YearMonth month = YearMonth.now();
        String endDay = month.atEndOfMonth().minusDays(1).toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String formatDateTimeNow = localDateTimeNow.format(formatter);
        if(endDay.equals(formatDateTimeNow)){
            if(!interestDatesFormattedToString.contains(formatDateTimeNow)){
                try {
                    List<Account> accounts = accountRepository.getAllAccounts();
                    for (Account account: accounts) {
                        BigDecimal interest = account.getBalance().multiply(interestRate).divide(new BigDecimal(100));
                        accountRepository.setDeposit(account.getId(), account.getBalance().add(interest));
                        transactionService.save(new Transaction(null, account.getId(), interest, account.getCurrency(), LocalDateTime.now(), TransactionType.INTEREST_CALCULATION, Status.APPROVED));
                        paymentCheckService.createPaymentCheckForInterestCalculation(account, interest.doubleValue(), TransactionType.INTEREST_CALCULATION);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                InterestDate interestDate = new InterestDate(localDateTimeNow);
                try {
                    interestDateRepository.saveInterestDate(interestDate);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

            }
        }

    }
}
