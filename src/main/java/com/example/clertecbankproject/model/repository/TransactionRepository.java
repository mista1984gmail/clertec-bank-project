package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    boolean saveTransaction(Transaction transaction) throws Exception;
    List<Transaction> getTransactionsForPeriod(Long id, String startDate, String endDate) throws Exception;

}
