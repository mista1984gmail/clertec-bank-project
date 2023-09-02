package com.example.clertecbankproject.service.impl;

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

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;

    public AccountServiceImpl() {
    }

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void addAccount(Account account) throws Exception {
        logger.info("Сохранение СЧЕТА: {}", account);
        boolean isAccountSaved = accountRepository.saveAccount(account);
        String success = isAccountSaved ? "" : "не";
        logger.info("СЧЕТ {} сохранен: {}", success, account);
    }

    @Override
    public Account getAccount(Long id) throws Exception {
        Account account = accountRepository.getAccount(id);
        if (account.getId()==null) {
            logger.debug("СЧЕТ с id={} не существует!",id);
        } else {
            logger.debug("СЧЕТ с id= '{}', {}", account.getId(), account);
        }
        return account;
    }

    @Override
    public synchronized void depositMoney(Account account, Double deposit) throws Exception {
            accountRepository.depositAccount(account, deposit);
        }

    @Override
    public void deleteAccount(Long id) throws Exception {
        logger.info("Удаление СЧЕТА с id= '{}'", id);
        accountRepository.deleteAccount(id);
    }

    @Override
    public List<AccountDto> getAllClientAccounts(Long id) throws Exception {
        return accountRepository.getAllClientAccounts(id);
    }
}
