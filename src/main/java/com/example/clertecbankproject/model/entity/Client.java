package com.example.clertecbankproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String address;
    private List<Account> accounts;

}
