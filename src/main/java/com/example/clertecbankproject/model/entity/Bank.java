package com.example.clertecbankproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    private Long id;
    private String bankName;
    private List<Client> clients;

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public Bank(Long id, String bankName) {
        this.id = id;
        this.bankName = bankName;
    }
}
