package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.impl.AccountRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.BankRepositoryImpl;
import com.example.clertecbankproject.service.AccountService;
import com.example.clertecbankproject.service.BankService;
import com.example.clertecbankproject.service.impl.util.FakeAccount;
import com.example.clertecbankproject.service.impl.util.FakeClient;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImplTest.class);

    public static final Long ID = 1L;
    private static AccountService accountService;

    private static AccountRepository accountRepository;

    @BeforeAll
    public static void init() {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);
    }
    @AfterEach
    public void resetMock() {
        reset(accountRepository);
    }

    @BeforeEach
    public void setUp() {
        logger.info("Test for 'AccountServiceImpl' are started.");
    }

    @AfterEach
    public void tearDown() {
        logger.info("Test for 'AccountServiceImpl' are finished.");
        logger.info("*****************************************************************************");
    }


    @Test
    void addAccount() throws Exception {
        //given
        Account account = FakeAccount.getFirstAccount();
        //when
        when(accountRepository.saveAccount(account)).thenReturn(true);
        accountService.addAccount(account);
        //then
        verify(accountRepository, times(1)).saveAccount(account);

    }

    @Test
    void getAccount() throws Exception {
        //given
        Account account = FakeAccount.getFirstAccount();
        //when
        when(accountRepository.getAccount(any())).thenReturn(account);
        Account accountFromDB = accountService.getAccount(ID);
        //then
        Assertions.assertEquals(account,accountFromDB);
        verify(accountRepository, times(1)).getAccount(any());
    }

    @Test
    void deleteAccount() throws Exception {
        //given
        Account account = FakeAccount.getFirstAccount();
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(true);
        //when
        accountService.addAccount(account);
        accountService.deleteAccount(ID);
        List<Account> accountsFromDB = accountRepository.getAllAccounts();
        //then
        Assert.assertEquals(accountsFromDB.size(),0);
    }
}