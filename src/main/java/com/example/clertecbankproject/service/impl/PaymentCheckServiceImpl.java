package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.*;
import com.example.clertecbankproject.model.entity.util.AccountStatement;
import com.example.clertecbankproject.model.entity.util.BillNumber;
import com.example.clertecbankproject.model.entity.util.PaymentCheck;
import com.example.clertecbankproject.model.entity.util.StatementNumber;
import com.example.clertecbankproject.model.repository.*;
import com.example.clertecbankproject.service.PaymentCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentCheckServiceImpl implements PaymentCheckService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentCheckServiceImpl.class);
    private BankRepository repository;
    private BillNumberRepository billNumberRepository;
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private StatementNumberRepository statementNumberRepository;

    public PaymentCheckServiceImpl(BankRepository repository, BillNumberRepository billNumberRepository, AccountRepository accountRepository, ClientRepository clientRepository, StatementNumberRepository statementNumberRepository) {
        this.repository = repository;
        this.billNumberRepository = billNumberRepository;
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.statementNumberRepository = statementNumberRepository;
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

    @Override
    public void createPaymentCheckForInterestCalculation(Account account, Double deposit, TransactionType transactionType) throws Exception {
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
            StringBuilder text = getBankCheckForInterestCalculation(account, paymentCheck);
            writer.write(text.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        billNumberRepository.saveBillNumber(new BillNumber(numberOfBill));
    }

    @Override
    public void createAccountStatement(Client client, Account account, List<Transaction> transactions, String startDate, String endDate) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH.mm");
        AccountStatement accountStatement = new AccountStatement();
        Bank bank = repository.getBank(account.getBankId());
        Long numberOfStatements = statementNumberRepository.getCountStatement() + 1L;
        accountStatement.setNameOfBank(bank.getBankName());
        accountStatement.setFullNameOfClient(client.getLastName() + " " + client.getFirstName() + " " + client.getSecondName());
        accountStatement.setAccountNumber(account.getAccountNumber());
        accountStatement.setCurrency(account.getCurrency().name());
        accountStatement.setOpeningDate(account.getRegistrationDate().format(formatter));
        accountStatement.setPeriod(startDate + " - " + endDate);
        accountStatement.setDateAndTimeOfFormation(LocalDateTime.now().format(formatter1));
        accountStatement.setBalance(account.getBalance().toString() + " " + account.getCurrency().name());
        accountStatement.setTransactions(transactions);
        getBankAccountStatement(accountStatement);
        String file = "src/main/resources/statement/" + numberOfStatements + "statement.txt";
        try(FileWriter writer = new FileWriter(file, false))
        {
            StringBuilder text = getBankAccountStatement(accountStatement);
            writer.write(text.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        statementNumberRepository.saveStatementNumber(new StatementNumber(numberOfStatements));
    }

    private StringBuilder getBankAccountStatement(AccountStatement accountStatement) {
        StringBuilder text = new StringBuilder();
        text.append("\n");
        text.append(statementBankLine(100, "Выписка"));
        text.append("\n");
        text.append(statementBankLine(100, accountStatement.getNameOfBank()));
        text.append("\n");
        text.append(formatLineStatement("Клиент", accountStatement.getFullNameOfClient()));
        text.append("\n");
        text.append(formatLineStatement("Счет",  accountStatement.getAccountNumber()));
        text.append("\n");
        text.append(formatLineStatement("Валюта", accountStatement.getCurrency()));
        text.append("\n");
        text.append(formatLineStatement("Дата открытия", accountStatement.getOpeningDate()));
        text.append("\n");
        text.append(formatLineStatement("Период", accountStatement.getPeriod()));
        text.append("\n");
        text.append(formatLineStatement("Дата и время формирования", accountStatement.getDateAndTimeOfFormation()));
        text.append("\n");
        text.append(formatLineStatement("Остаток", accountStatement.getBalance()));
        text.append("\n");
        text.append("     Дата          |              Примечание                |  Сумма");
        text.append("\n");
        text.append("----------------------------------------------------------------------");
        text.append("\n");
        text.append(formationLineStatement(accountStatement));
        return text;
    }

    private StringBuilder getBankCheckForInterestCalculation(Account account, PaymentCheck paymentCheck) {
        StringBuilder text = new StringBuilder();
        text.append(firstAndLastLine());
        text.append("\n");
        text.append(bankCheckLine(50, "Банковский Чек"));
        text.append("\n");
        text.append(formatLine("| Чек: ", paymentCheck.getNumber().toString()));
        text.append("\n");
        text.append(formatLine("| " + formatDate(paymentCheck.getPaymentCheckDate()), formatTime(paymentCheck.getPaymentCheckDate())));
        text.append("\n");
        text.append(formatLine("| Тип транзакции: ", "Начисление процентов"));
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

    private StringBuilder getBankCheckForWithdrawal(Account account, PaymentCheck paymentCheck) {
        StringBuilder text = new StringBuilder();
        text.append(firstAndLastLine());
        text.append("\n");
        text.append(bankCheckLine(50, "Банковский Чек"));
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
        text.append(bankCheckLine(50, "Банковский Чек"));
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
        text.append(bankCheckLine(50,"Банковский Чек"));
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
    private String formationLineStatement(AccountStatement accountStatement) {
        List<Transaction> transactions = accountStatement.getTransactions();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < transactions.size(); i++) {
            sb.append("   ");
            sb.append(formatDate(transactions.get(i).getTransactionTime()));
            sb.append("      | ");
            switch (transactions.get(i).getTransactionType().name()){
                case "WITHDRAWAL":
                    sb.append(statementNoteLine(39, "Снятие денег"));
                    break;
                case "REPLENISHMENT":
                    Account sourceAccount = null;
                    try {
                        sourceAccount = accountRepository.getAccount(transactions.get(i).getSourceAccountId());
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    Client sourceClient = null;
                    try {
                        sourceClient = clientRepository.getClient(sourceAccount.getClientId());
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    if(transactions.get(i).getAmount().compareTo(new BigDecimal(0)) < 0){
                        sb.append(statementNoteLine(39, "Перевод денег " + sourceClient.getLastName()));
                    } else {
                        sb.append(statementNoteLine(39, "Перевод денег от " + sourceClient.getLastName()));
                    }
                    break;
                case "INTEREST_CALCULATION":
                    sb.append(statementNoteLine(39, "Начисление процентов"));
                    break;
                case "DEPOSIT":
                    sb.append(statementNoteLine(39, "Пополнение счета"));
                    break;
            }
            sb.append("  ");
            sb.append(transactions.get(i).getAmount().setScale(2, RoundingMode.HALF_UP));
            sb.append(" ");
            sb.append(transactions.get(i).getCurrency());
            sb.append("\n");
        }
        return sb.toString();
    }
    public String formatLineStatement(String line, String value){
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        sb.append(line);
        int sizeSpace = 50 - line.length();
        for (int i = 0; i < sizeSpace; i++) {
            sb.append(" ");
        }
        sb.append("|");
        sb.append(value);
        return sb.toString();
    }
    public String firstAndLastLine(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
    public String bankCheckLine(int sizeLine, String world){
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        int sizeSpace = (sizeLine - world.length())/2;
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
    public String statementBankLine(int sizeLine, String world){
        StringBuilder sb = new StringBuilder();
        int sizeSpace = (sizeLine - world.length())/2;
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
        return sb.toString();
    }
    public String statementNoteLine(int sizeLine, String world){
        StringBuilder sb = new StringBuilder();
        int sizeSpace = (sizeLine - world.length());
        sb.append(world);
        for (int i = 0; i < sizeSpace; i++) {
            sb.append(" ");
        }
        sb.append("|");
        return sb.toString();
    }
}
