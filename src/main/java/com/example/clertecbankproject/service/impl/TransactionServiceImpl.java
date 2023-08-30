package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Currency;
import com.example.clertecbankproject.model.entity.Status;
import com.example.clertecbankproject.model.entity.Transaction;
import com.example.clertecbankproject.model.entity.TransactionType;
import com.example.clertecbankproject.model.repository.TransactionRepository;
import com.example.clertecbankproject.model.repository.impl.TransactionRepositoryImpl;
import com.example.clertecbankproject.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private TransactionRepository transactionRepository = new TransactionRepositoryImpl();

    public TransactionServiceImpl() {
    }

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public synchronized Transaction deposit(Long id, Currency currency, Double deposit) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(null);
        transaction.setTargetAccountId(id);
        transaction.setAmount(new BigDecimal(deposit));
        transaction.setCurrency(currency);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setStatus(Status.APPROVED);
        transactionRepository.saveTransaction(transaction);
        return transaction;
    }

    @Override
    public synchronized Transaction save(Transaction transaction) throws Exception {
        logger.info("Сохраняем транзакцию: {}", transaction);
        boolean isTransactionSaved = transactionRepository.saveTransaction(transaction);
        String success = isTransactionSaved ? "" : "не";
        logger.info("Транзакция {} сохранена: {}", success, transaction);
        return transaction;
    }

    @Override
    public synchronized Transaction withdrawal(Long id, Currency currency, Double deposit) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(null);
        transaction.setTargetAccountId(id);
        transaction.setAmount(new BigDecimal(deposit).multiply(new BigDecimal(-1)));
        transaction.setCurrency(currency);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setStatus(Status.APPROVED);
        transactionRepository.saveTransaction(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsForPeriod(Long id, String startDate, String endDate) throws Exception {
        List<Transaction> transactions = transactionRepository.getTransactionsForPeriod(id, startDate, endDate);
        return transactions;
    }
}
