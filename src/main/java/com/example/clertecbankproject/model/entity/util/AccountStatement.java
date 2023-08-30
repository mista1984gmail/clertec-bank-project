package com.example.clertecbankproject.model.entity.util;

import com.example.clertecbankproject.model.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatement {
    private String nameOfBank;
    private String fullNameOfClient;
    private String accountNumber;
    private String currency;
    private String openingDate;
    private String period;
    private String dateAndTimeOfFormation;
    private String balance;
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return "AccountStatement{" +
                "nameOfBank='" + nameOfBank + '\'' +
                ", fullNameOfClient='" + fullNameOfClient + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", openingDate='" + openingDate + '\'' +
                ", period='" + period + '\'' +
                ", dateAndTimeOfFormation='" + dateAndTimeOfFormation + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
