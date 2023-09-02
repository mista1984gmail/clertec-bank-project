package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.impl.BankRepositoryImpl;
import com.example.clertecbankproject.service.BankService;
import com.example.clertecbankproject.service.impl.util.FakeBank;
import com.example.clertecbankproject.service.impl.util.FakeClient;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BankServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(BankServiceImplTest.class);

    public static final Long ID = 1L;
    private static BankService bankService;

    private static BankRepository bankRepository;

    @BeforeAll
    public static void init() {
        bankRepository = mock(BankRepositoryImpl.class);
        bankService = new BankServiceImpl(bankRepository);
    }
    @AfterEach
    public void resetMock() {
        reset(bankRepository);
    }

    @BeforeEach
    public void setUp() {
        logger.info("Test for 'BankServiceImpl' are started.");
    }

    @AfterEach
    public void tearDown() {
        logger.info("Test for 'BankServiceImpl' are finished.");
        logger.info("*****************************************************************************");
    }

    @Test
    void addBank() throws Exception {
        //given
        Bank bank = FakeBank.getFirstBank();
        //when
        when(bankRepository.saveBank(bank)).thenReturn(true);
        bankService.addBank(bank);
        //then
        verify(bankRepository, times(1)).saveBank(bank);
    }

    @Test
    void getAllBanks() throws Exception {
        //given
        Bank firstBank = FakeBank.getFirstBank();
        List<Bank> banks = new ArrayList<>();
        banks.add(firstBank);
        //when
        when(bankRepository.getAllBanks()).thenReturn(banks);
        List<Bank> banksFromDB = bankService.getAllBanks();
        //then
        Assertions.assertEquals(banks,banksFromDB);
        verify(bankRepository, times(1)).getAllBanks();
    }

    @Test
    void deleteBank() throws Exception {
        //given
        Bank bank = FakeBank.getFirstBank();
        when(bankRepository.saveBank(any(Bank.class))).thenReturn(true);
        //when
        bankService.addBank(bank);
        bankService.deleteBank(ID);
        List<Bank> banksFromDB = bankRepository.getAllBanks();
        //then
        Assert.assertEquals(banksFromDB.size(),0);
    }

    @Test
    void getBank() throws Exception {
        //given
        Bank bank = FakeBank.getFirstBank();
        //when
        when(bankRepository.getBank(any())).thenReturn(bank);
        Bank bankFromDB = bankService.getBank(ID);
        //then
        Assertions.assertEquals(bank,bankFromDB);
        verify(bankRepository, times(1)).getBank(any());
    }
}