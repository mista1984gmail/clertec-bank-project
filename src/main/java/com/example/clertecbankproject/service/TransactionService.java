package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Currency;
import com.example.clertecbankproject.model.entity.Transaction;

public interface TransactionService {

    void deposit(Long id, Currency currency, Double deposit) throws Exception;
    Transaction save(Transaction transaction) throws Exception;
    void withdrawal(Long id, Currency currency, Double deposit) throws Exception;
}
