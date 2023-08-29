package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.entity.Transaction;

public interface TransactionRepository {
    boolean saveTransaction(Transaction transaction) throws Exception;
}
