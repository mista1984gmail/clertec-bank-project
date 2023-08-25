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
    private String name;
    private List<Client> clients;
}
