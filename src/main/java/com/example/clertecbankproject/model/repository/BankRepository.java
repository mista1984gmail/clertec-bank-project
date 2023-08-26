package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    boolean saveBank(Bank bank) throws Exception;
    List<Bank> getAllBanks() throws Exception;
    void deleteBank(Long id) throws Exception;
    Bank getBank(Long id) throws Exception;

    void updateBank(Long idForUpdate, String nameOfBank);
}
