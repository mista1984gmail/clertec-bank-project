package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;

import java.util.List;

public interface BankRepository {
    boolean saveBank(Bank bank) throws Exception;
    List<Bank> getAllBanks() throws Exception;
    void deleteBank(Long id) throws Exception;
    Bank getBank(Long id) throws Exception;

    void updateBank(Long idForUpdate, String nameOfBank);

    void addClientToBank(Long clientId, Long bankId);

    List<Client> showAllBankClients(Long bankId) throws Exception;
}
