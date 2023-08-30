package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Currency;
import com.example.clertecbankproject.model.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction deposit(Long id, Currency currency, Double deposit) throws Exception;
    Transaction save(Transaction transaction) throws Exception;
    Transaction withdrawal(Long id, Currency currency, Double deposit) throws Exception;

    List<Transaction> getTransactionsForPeriod(Long id, String startDate, String endDate) throws Exception;
}
