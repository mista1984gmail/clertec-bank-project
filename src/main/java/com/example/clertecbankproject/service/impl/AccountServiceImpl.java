package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.*;
import com.example.clertecbankproject.model.entity.dto.AccountDto;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.model.repository.TransactionRepository;
import com.example.clertecbankproject.service.AccountNumberGenerationService;
import com.example.clertecbankproject.service.AccountService;
import com.example.clertecbankproject.service.PaymentCheckService;
import com.example.clertecbankproject.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.function.Consumer;

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    public static final Consumer<AccountDto> LOG_ACTION = account ->
            logger.debug("{}", account);

    private AccountRepository accountRepository;
    private TransactionService transactionService;
    private PaymentCheckService paymentCheckService;

    public AccountServiceImpl() {
    }

    public AccountServiceImpl(AccountRepository accountRepository, TransactionService transactionService, PaymentCheckService paymentCheckService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.paymentCheckService = paymentCheckService;
    }

    @Override
    public void addAccount() throws Exception {
        Account account = new Account();
        logger.info("Input information about Account");
        AccountNumberGenerationService accountNumberGenerationService = new AccountNumberGenerationService();
        account.setAccountNumber(accountNumberGenerationService.generateAccountNumber());
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter client id: ");
        Long clientId = Long.parseLong(scanner.nextLine());
        account.setClientId(clientId);
        logger.info("Enter bank id: ");
        Long bankId = Long.parseLong(scanner.nextLine());
        account.setBankId(bankId);
        account.setBalance(new BigDecimal(0));
        account.setCurrency(Currency.BYN);
        account.setRegistrationDate(LocalDateTime.now());
        saveAccount(account);
    }

    @Override
    public Account getAccount() throws Exception {
        Long idForShow;
        logger.info("Input id account for show");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Trying to get account with id = '{}'", idForShow);
        Account account = accountRepository.getAccount(idForShow);
        if (account.getId()==null) {
            logger.debug("Account with id={} don't exist",idForShow);
        } else {
            logger.debug("Account with id= '{}', {}", account.getId(), account);
        }
        return account;
    }

    @Override
    public synchronized void depositAccount() throws Exception {
        Long id;
        logger.info("Input id account for deposit");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextLong();
        logger.info("Input deposit");
        Double deposite = scanner.nextDouble();
        logger.info("Trying to get account with id = '{}'", id);
        Account account = accountRepository.getAccount(id);
        if (account.getId()==null) {
            logger.debug("Account with id={} don't exist",id);
        } else {
            logger.debug("Account with id= '{}', {}", account.getId(), account);
            accountRepository.depositAccount(account, deposite);
            transactionService.deposit(account.getId(), account.getCurrency(), deposite);
        }
    }

    @Override
    public void deleteAccount() throws Exception {
        Long idForDelete;
        logger.info("Input id account for delete");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        deleteAccount(idForDelete);
    }

    @Override
    public void getAllClientAccounts() throws Exception {
        Long idForShow;
        logger.info("Input id client for show all accounts:");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        accountRepository.getAllClientAccounts(idForShow).forEach(LOG_ACTION);
    }

    @Override
    public synchronized Transaction replenishmentMoney() throws Exception {
        Long sourceAccountId;
        Long targetAccountId;
        logger.info("Input id account for replenishment money: ");
        Scanner scanner = new Scanner(System.in);
        sourceAccountId = scanner.nextLong();
        logger.info("Input id account where to replenishment money: ");
        targetAccountId = scanner.nextLong();
        logger.info("Input amount to replenishment money:");
        Double deposit = scanner.nextDouble();
        logger.info("Trying to get account with id = '{}'", sourceAccountId);
        Account sourceAccount = accountRepository.getAccount(sourceAccountId);
        logger.info("Trying to get account with id = '{}'", targetAccountId);
        Account targetAccount = accountRepository.getAccount(targetAccountId);
        if (sourceAccount.getId()==null) {
            logger.debug("Source account with id {} don't exist", sourceAccount.getId());
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (targetAccount.getId()==null) {
            logger.debug("Target account with id {} don't exist", sourceAccount.getId());
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (new BigDecimal(deposit).compareTo(new BigDecimal(0)) < 0) {
            logger.debug("Transfer amount must be positive, but {}", deposit);
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        if (sourceAccount.getBalance().compareTo(new BigDecimal(deposit)) < 0) {
            logger.debug("Not enough money on source account");
            return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.CANCELED));
        }
        accountRepository.depositAccount(sourceAccount, -deposit);
        transactionService.save(new Transaction(targetAccountId, sourceAccountId, new BigDecimal(deposit).multiply(new BigDecimal(-1)), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.APPROVED));
        accountRepository.depositAccount(targetAccount, deposit);
        paymentCheckService.createPaymentCheck(sourceAccount, targetAccount, deposit, TransactionType.REPLENISHMENT);
        return transactionService.save(new Transaction(sourceAccountId, targetAccountId, new BigDecimal(deposit), sourceAccount.getCurrency(), LocalDateTime.now(), TransactionType.REPLENISHMENT, Status.APPROVED));
    }

    @Override
    public synchronized void withdrawalMoney() throws Exception {
        Long id;
        logger.info("Input id account for withdrawal");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextLong();
        logger.info("Input deposit");
        Double deposite = scanner.nextDouble();
        logger.info("Trying to get account with id = '{}'", id);
        Account account = accountRepository.getAccount(id);
        if (account.getId()==null) {
            logger.debug("Account with id={} don't exist",id);
        }
        else {
            logger.debug("Account with id= '{}', {}", account.getId(), account);
            accountRepository.depositAccount(account, -deposite);
            transactionService.withdrawal(account.getId(), account.getCurrency(), deposite);
        }
    }

    public void deleteAccount(Long id) throws Exception {
        logger.info("Trying to delete account with id= '{}'", id);
        accountRepository.deleteAccount(id);
    }

    public void saveAccount(Account account) throws Exception {
        logger.info("Trying to save account: {}", account);
        boolean isAccountSaved = accountRepository.saveAccount(account);
        String success = isAccountSaved ? "" : "not ";
        logger.info("Account was {}saved: {}", success, account);
    }
}
