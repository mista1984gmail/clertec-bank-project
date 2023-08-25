package com.example.clertecbankproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;
    private String accountNumber;
    private Long clientId;
    private Long bankId;
    private BigDecimal balance;
    private Currency currency;
    private LocalDateTime registrationDate;
}
