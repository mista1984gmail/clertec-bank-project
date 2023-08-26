package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Bank;

public interface BankService {
    void addBank()throws Exception;
    void getAllBanks() throws Exception;
    void deleteBank () throws Exception;
    Bank getBank() throws Exception;
    void updateBank () throws Exception;
}
