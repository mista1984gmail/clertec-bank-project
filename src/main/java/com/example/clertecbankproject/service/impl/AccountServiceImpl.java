package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.entity.Currency;
import com.example.clertecbankproject.model.entity.dto.AccountDto;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.service.AccountNumberGenerationService;
import com.example.clertecbankproject.service.AccountService;
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

    public AccountServiceImpl() {
    }

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
