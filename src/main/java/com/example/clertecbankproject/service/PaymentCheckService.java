package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.TransactionType;

public interface PaymentCheckService {
    void createPaymentCheckForReplenishment(Account sourceAccount, Account targetAccount, Double deposit, TransactionType transactionType) throws Exception;

    void createPaymentCheckForDeposit(Account account, Double deposit, TransactionType transactionType) throws Exception;

    void createPaymentCheckForWithdrawal(Account account, Double deposit, TransactionType transactionType) throws Exception;
}
