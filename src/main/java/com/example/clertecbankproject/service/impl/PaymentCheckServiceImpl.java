package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.*;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.BillNumberRepository;
import com.example.clertecbankproject.service.PaymentCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentCheckServiceImpl implements PaymentCheckService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentCheckServiceImpl.class);
    private BankRepository repository;
    private BillNumberRepository billNumberRepository;

    public PaymentCheckServiceImpl(BankRepository repository, BillNumberRepository billNumberRepository) {
        this.repository = repository;
        this.billNumberRepository = billNumberRepository;
    }

    @Override
    public void createPaymentCheckForReplenishment(Account sourceAccount, Account targetAccount, Double deposit, TransactionType transactionType) throws Exception {
        PaymentCheck paymentCheck = new PaymentCheck();
        Bank sourceBank = repository.getBank(sourceAccount.getBankId());
        String sourceBankName = sourceBank.getBankName();
        Bank targetBank = repository.getBank(targetAccount.getBankId());
        String targetBankName = targetBank.getBankName();
        Long numberOfBill = billNumberRepository.getCountBills() + 1L;
        paymentCheck.setNumber(numberOfBill);
        paymentCheck.setPaymentCheckDate(LocalDateTime.now());
        paymentCheck.setTransactionType(transactionType);
        paymentCheck.setSourceBankName(sourceBankName);
        paymentCheck.setTargetBankName(targetBankName);
        paymentCheck.setSourceAccountNumber(sourceAccount.getAccountNumber());
        paymentCheck.setTargetAccountNumber(targetAccount.getAccountNumber());
        paymentCheck.setAmount(new BigDecimal(deposit));
        logger.info("{}",paymentCheck);
        String file = "src/main/resources/bills/" + numberOfBill + "bill.txt";
        try(FileWriter writer = new FileWriter(file, false))
        {
            StringBuilder text = getBankCheckForReplenishment(sourceAccount, paymentCheck);
            writer.write(text.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        billNumberRepository.saveBillNumber(new BillNumber(numberOfBill));
    }

    @Override
    public void createPaymentCheckForDeposit(Account account, Double deposit, TransactionType transactionType) throws Exception {
        PaymentCheck paymentCheck = new PaymentCheck();
        Bank targetBank = repository.getBank(account.getBankId());
        String targetBankName = targetBank.getBankName();
        Long numberOfBill = billNumberRepository.getCountBills() + 1L;
        paymentCheck.setNumber(numberOfBill);
        paymentCheck.setPaymentCheckDate(LocalDateTime.now());
        paymentCheck.setTransactionType(transactionType);
        paymentCheck.setSourceBankName(null);
        paymentCheck.setTargetBankName(targetBankName);
        paymentCheck.setSourceAccountNumber(null);
        paymentCheck.setTargetAccountNumber(account.getAccountNumber());
        paymentCheck.setAmount(new BigDecimal(deposit));
        logger.info("{}",paymentCheck);
        String file = "src/main/resources/bills/" + numberOfBill + "bill.txt";
        try(FileWriter writer = new FileWriter(file, false))
        {
            StringBuilder text = getBankCheckForDeposit(account, paymentCheck);
            writer.write(text.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        billNumberRepository.saveBillNumber(new BillNumber(numberOfBill));

    }

    @Override
    public void createPaymentCheckForWithdrawal(Account account, Double deposit, TransactionType transactionType) throws Exception {
        PaymentCheck paymentCheck = new PaymentCheck();
        Bank sourceBank = repository.getBank(account.getBankId());
        String sourceBankName = sourceBank.getBankName();
        Long numberOfBill = billNumberRepository.getCountBills() + 1L;
        paymentCheck.setNumber(numberOfBill);
        paymentCheck.setPaymentCheckDate(LocalDateTime.now());
        paymentCheck.setTransactionType(transactionType);
        paymentCheck.setSourceBankName(sourceBankName);
        paymentCheck.setTargetBankName(null);
        paymentCheck.setSourceAccountNumber(account.getAccountNumber());
        paymentCheck.setTargetAccountNumber(null);
        paymentCheck.setAmount(new BigDecimal(deposit));
        logger.info("{}",paymentCheck);
        String file = "src/main/resources/bills/" + numberOfBill + "bill.txt";
        try(FileWriter writer = new FileWriter(file, false))
        {
            StringBuilder text = getBankCheckForWithdrawal(account, paymentCheck);
            writer.write(text.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        billNumberRepository.saveBillNumber(new BillNumber(numberOfBill));
    }

    private StringBuilder getBankCheckForWithdrawal(Account account, PaymentCheck paymentCheck) {
        StringBuilder text = new StringBuilder();
        text.append(firstAndLastLine());
        text.append("\n");
        text.append(bankCheckLine("Банковский Чек"));
        text.append("\n");
        text.append(formatLine("| Чек: ", paymentCheck.getNumber().toString()));
        text.append("\n");
        text.append(formatLine("| " + formatDate(paymentCheck.getPaymentCheckDate()), formatTime(paymentCheck.getPaymentCheckDate())));
        text.append("\n");
        text.append(formatLine("| Тип транзакции: ", "Снятие"));
        text.append("\n");
        text.append(formatLine("| Банк отправителя: ", paymentCheck.getSourceBankName()));
        text.append("\n");
        text.append(formatLine("| Счет отправителя: ", paymentCheck.getSourceAccountNumber()));
        text.append("\n");
        text.append(formatLine("| Сумма: ", paymentCheck.getAmount().setScale(2) + " " + account.getCurrency()));
        text.append("\n");
        text.append(firstAndLastLine());
        return text;
    }

    private StringBuilder getBankCheckForDeposit(Account account, PaymentCheck paymentCheck) {
        StringBuilder text = new StringBuilder();
        text.append(firstAndLastLine());
        text.append("\n");
        text.append(bankCheckLine("Банковский Чек"));
        text.append("\n");
        text.append(formatLine("| Чек: ", paymentCheck.getNumber().toString()));
        text.append("\n");
        text.append(formatLine("| " + formatDate(paymentCheck.getPaymentCheckDate()), formatTime(paymentCheck.getPaymentCheckDate())));
        text.append("\n");
        text.append(formatLine("| Тип транзакции: ", "Пополнение"));
        text.append("\n");
        text.append(formatLine("| Банк получателя: ", paymentCheck.getTargetBankName()));
        text.append("\n");
        text.append(formatLine("| Счет получателя: ", paymentCheck.getTargetAccountNumber()));
        text.append("\n");
        text.append(formatLine("| Сумма: ", paymentCheck.getAmount().setScale(2) + " " + account.getCurrency()));
        text.append("\n");
        text.append(firstAndLastLine());
        return text;
    }

    private StringBuilder getBankCheckForReplenishment(Account sourceAccount, PaymentCheck paymentCheck) {
        StringBuilder text = new StringBuilder();
        text.append(firstAndLastLine());
        text.append("\n");
        text.append(bankCheckLine("Банковский Чек"));
        text.append("\n");
        text.append(formatLine("| Чек: ", paymentCheck.getNumber().toString()));
        text.append("\n");
        text.append(formatLine("| " + formatDate(paymentCheck.getPaymentCheckDate()), formatTime(paymentCheck.getPaymentCheckDate())));
        text.append("\n");
        text.append(formatLine("| Тип транзакции: ", "Перевод"));
        text.append("\n");
        text.append(formatLine("| Банк отправителя: ", paymentCheck.getSourceBankName()));
        text.append("\n");
        text.append(formatLine("| Банк получателя: ", paymentCheck.getTargetBankName()));
        text.append("\n");
        text.append(formatLine("| Счет отправителя: ", paymentCheck.getSourceAccountNumber()));
        text.append("\n");
        text.append(formatLine("| Счет получателя: ", paymentCheck.getTargetAccountNumber()));
        text.append("\n");
        text.append(formatLine("| Сумма: ", paymentCheck.getAmount().setScale(2) + " " + sourceAccount.getCurrency()));
        text.append("\n");
        text.append(firstAndLastLine());
        return text;
    }

    public String formatDate(LocalDateTime ldt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDateTime = ldt.format(formatter);
        return formatDateTime;
    }
    public String formatTime(LocalDateTime ldt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatDateTime = ldt.format(formatter);
        return formatDateTime;
    }
    public String formatLine(String line, String value){
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        int sizeSpace = 50 - line.length() - value.length();
        for (int i = 0; i < sizeSpace; i++) {
            sb.append(" ");
        }
        sb.append(value);
        sb.append("|");
        return sb.toString();
    }
    public String firstAndLastLine(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
    public String bankCheckLine(String world){
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        int sizeSpace = (50 - world.length())/2;
        for (int i = 0; i < sizeSpace; i++) {
            sb.append(" ");
        }
        sb.append(world);
        if(world.length()%2==0){
            sizeSpace--;
        }
        for (int i = 0; i < sizeSpace; i++) {
            sb.append(" ");
        }
        sb.append("|");
        return sb.toString();
    }
}
