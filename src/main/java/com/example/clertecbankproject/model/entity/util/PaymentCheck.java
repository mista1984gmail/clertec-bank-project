package com.example.clertecbankproject.model.entity.util;

import com.example.clertecbankproject.model.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCheck {
    private Long number;
    private LocalDateTime paymentCheckDate;
    private TransactionType transactionType;
    private String sourceBankName;
    private String targetBankName;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;

}
