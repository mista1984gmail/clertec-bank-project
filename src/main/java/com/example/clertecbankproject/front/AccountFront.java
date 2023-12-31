package com.example.clertecbankproject.front;

import com.example.clertecbankproject.model.entity.*;
import com.example.clertecbankproject.model.entity.dto.AccountDto;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class AccountFront {
    private static final Logger logger = LoggerFactory.getLogger(AccountFront.class);
    public static final Consumer<AccountDto> LOG_ACTION = account ->
            logger.debug("{}", account);

    private AccountService accountService;
    private ClientService clientService;
    private TransactionService transactionService;
    private PaymentCheckService paymentCheckService;

    public AccountFront() {
    }

    public AccountFront(AccountService accountService, ClientService clientService, TransactionService transactionService, PaymentCheckService paymentCheckService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.paymentCheckService = paymentCheckService;
    }


    public void addAccount() throws Exception {
        Account account = new Account();
        logger.info("Введите информацию для открытия СЧЕТА:");
        AccountNumberGenerationService accountNumberGenerationService = new AccountNumberGenerationService();
        account.setAccountNumber(accountNumberGenerationService.generateAccountNumber());
        Scanner scanner = new Scanner(System.in);
        logger.info("Введите id клиента: ");
        Long clientId = Long.parseLong(scanner.nextLine());
        account.setClientId(clientId);
        logger.info("Введите id банка: ");
        Long bankId = Long.parseLong(scanner.nextLine());
        account.setBankId(bankId);
        account.setBalance(new BigDecimal(0));
        account.setCurrency(Currency.BYN);
        account.setRegistrationDate(LocalDateTime.now());
        accountService.addAccount(account);
    }


    public Account getAccount() throws Exception {
        Long idForShow;
        logger.info("Введите id СЧЕТА, который нужно отобразить:");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Получение СЧЕТА по id = '{}'", idForShow);
        Account account = accountService.getAccount(idForShow);
        return account;
    }


    public synchronized Transaction depositMoney() throws Exception {
        Long id;
        logger.info("Введите id СЧЕТА для пополнения:");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextLong();
        logger.info("Введите сумму:");
        Double deposit = scanner.nextDouble();
        logger.info("Получение СЧЕТА по id = '{}'", id);
        Account account = accountService.getAccount(id);
        if (account.getId()==null) {
            logger.debug("СЧЕТ с id={} не существует!",id);
            return transactionService.save(new Transaction(null, null, new BigDecimal(deposit), null, LocalDateTime.now(), TransactionType.DEPOSIT, Status.CANCELED));
        }
        if (new BigDecimal(deposit).compareTo(new BigDecimal(0)) < 0) {
            logger.debug("Сумма пополнения должна быть положительной, Вы ввели {}!", deposit);
            return transactionService.save(new Transaction(null, account.getId(), new BigDecimal(deposit), account.getCurrency(), LocalDateTime.now(), TransactionType.DEPOSIT, Status.CANCELED));
        }
            logger.debug("СЧЕТ с id= '{}', {}", account.getId(), account);
            accountService.depositMoney(account, deposit);
            paymentCheckService.createPaymentCheckForDeposit(account, deposit, TransactionType.DEPOSIT);
            return transactionService.deposit(account.getId(), account.getCurrency(), deposit);
        }

    public void deleteAccount() throws Exception {
        Long idForDelete;
        logger.info("Введите id СЧЕТА для его удаления:");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        accountService.deleteAccount(idForDelete);
    }


    public void getAllClientAccounts() throws Exception {
        Long idForShow;
        logger.info("Введите id КЛИЕНТА для отображения всех его СЧЕТОВ:");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        accountService.getAllClientAccounts(idForShow).forEach(LOG_ACTION);
    }


    public synchronized Transaction replenishmentMoney() throws Exception {
        Long sourceAccountId;
        Long targetAccountId;
        logger.info("Введите id СЧЕТА, с которого надо сделать перевод денег: ");
        Scanner scanner = new Scanner(System.in);
        sourceAccountId = scanner.nextLong();
        logger.info("Введите id СЧЕТА, на который надо сделать перевод денег: ");
        targetAccountId = scanner.nextLong();
        logger.info("Введите сумму перевода:");
        Double deposit = scanner.nextDouble();
        logger.info("Получение СЧЕТА по id = '{}'", sourceAccountId);
        Account sourceAccount = accountService.getAccount(sourceAccountId);
        logger.info("Получение СЧЕТА по id = '{}'", targetAccountId);
        Account targetAccount = accountService.getAccount(targetAccountId);
        if (sourceAccount.getId()==null) {
            logger.debug("СЧЕТ-отправитель с id {} не существует!", sourceAccount.getId());
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (targetAccount.getId()==null) {
            logger.debug("СЧЕТ-получатель с id {} не существует!", sourceAccount.getId());
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (new BigDecimal(deposit).compareTo(new BigDecimal(0)) < 0) {
            logger.debug("Сумма пополнения должна быть положительной, Вы ввели {}!", deposit);
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (sourceAccount.getBalance().compareTo(new BigDecimal(deposit)) < 0) {
            logger.debug("Недостаточно денег на счете!");
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        accountService.depositMoney(sourceAccount, -deposit);
        transactionService.save(new Transaction(targetAccountId, sourceAccountId, new BigDecimal(deposit).multiply(new BigDecimal(-1)), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.APPROVED));
        accountService.depositMoney(targetAccount, deposit);
        paymentCheckService.createPaymentCheckForReplenishment(sourceAccount, targetAccount, deposit, TransactionType.REPLENISHMENT);
        return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.APPROVED));
    }


    public synchronized Transaction withdrawalMoney() throws Exception {
        Long id;
        logger.info("Введите id СЧЕТА для снятия денег:");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextLong();
        logger.info("Введите сумму, которую Вы хотите снять:");
        Double deposit = scanner.nextDouble();
        logger.info("Получение СЧЕТА по id = '{}'", id);
        Account account = accountService.getAccount(id);
        if (account.getId()==null) {
            logger.debug("СЧЕТ с id {} не существует!",id);
            return transactionService.save(new Transaction(null, null, new BigDecimal(deposit), null, LocalDateTime.now(), TransactionType.DEPOSIT, Status.CANCELED));
        }
        if (new BigDecimal(deposit).compareTo(new BigDecimal(0)) < 0) {
            logger.debug("Сумма снятия должна быть положительной, Вы ввели {}!", deposit);
            return transactionService.save(new Transaction(null, account.getId(), new BigDecimal(deposit), account.getCurrency(), LocalDateTime.now(), TransactionType.DEPOSIT, Status.CANCELED));
        }
        if (account.getBalance().compareTo(new BigDecimal(deposit)) < 0) {
            logger.debug("Недостаточно денег на счете!");
            return transactionService.save(new Transaction(null, account.getId(), new BigDecimal(deposit), account.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
            logger.debug("СЧЕТ с id= '{}', {}", account.getId(), account);
            accountService.depositMoney(account, -deposit);
            paymentCheckService.createPaymentCheckForWithdrawal(account, deposit, TransactionType.WITHDRAWAL);
            return transactionService.withdrawal(account.getId(), account.getCurrency(), deposit);

    }


    public void generationAccountStatement() throws Exception {
        Long id;
        logger.info("Введите id СЧЕТА для формирования выписки:");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextLong();
        logger.info("Введите начало периода выписки в формате день.месяц.год (пример: 01.01.2023):");
        Scanner scanner1 = new Scanner(System.in);
        String startDate = scanner1.nextLine();
        logger.info("Введите окончание периода выписки в формате день.месяц.год (пример: 01.06.2023):");
        String endDate = scanner1.nextLine();
        List<Transaction> transactions = transactionService.getTransactionsForPeriod(id, startDate, endDate);
        Account account = accountService.getAccount(id);
        if (account.getId()==null) {
            logger.debug("СЧЕТ-отправитель с id {} не существует!", account.getId());}
        else {
            Client client = clientService.getClientById(account.getClientId());
            paymentCheckService.createAccountStatement(client, account, transactions, startDate, endDate);
        }
    }

}
