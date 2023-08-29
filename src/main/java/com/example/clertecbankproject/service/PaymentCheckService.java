package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.TransactionType;

public interface PaymentCheckService {
    void createPaymentCheck(Account sourceAccount, Account targetAccount, Double deposit, TransactionType transactionType) throws Exception;
}
