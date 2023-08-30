package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.entity.Transaction;
import com.example.clertecbankproject.model.entity.TransactionType;

import java.util.List;

public interface PaymentCheckService {
    void createPaymentCheckForReplenishment(Account sourceAccount, Account targetAccount, Double deposit, TransactionType transactionType) throws Exception;

    void createPaymentCheckForDeposit(Account account, Double deposit, TransactionType transactionType) throws Exception;

    void createPaymentCheckForWithdrawal(Account account, Double deposit, TransactionType transactionType) throws Exception;
    void createPaymentCheckForInterestCalculation(Account account, Double deposit, TransactionType transactionType) throws Exception;

    void createAccountStatement(Client client, Account account, List<Transaction> transactions, String startDate, String endDate) throws Exception;
}
