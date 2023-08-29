package com.example.clertecbankproject.model.entity.dto;

import com.example.clertecbankproject.model.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Currency currency;
    private LocalDateTime registrationDate;
    private String bankName;

}
