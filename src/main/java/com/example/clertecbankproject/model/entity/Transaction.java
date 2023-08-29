package com.example.clertecbankproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime transactionTime;
    private TransactionType transactionType;
    private Status status;

    public Transaction(Long sourceAccountId, Long targetAccountId, BigDecimal amount, Currency currency, LocalDateTime transactionTime, TransactionType transactionType, Status status) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
        this.transactionTime = transactionTime;
        this.transactionType = transactionType;
        this.status = status;
    }
}
