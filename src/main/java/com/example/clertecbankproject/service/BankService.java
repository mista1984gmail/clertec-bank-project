package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;

import java.util.List;

public interface BankService {
    void addBank(Bank bank)throws Exception;
    List<Bank> getAllBanks() throws Exception;
    void deleteBank (Long id) throws Exception;
    Bank getBank(Long id) throws Exception;
    void updateBank (Long id, String nameOfBank);
    void addClientToBank(Long idBank, Long idClient);
    List<Client> showAllBankClients(Long id) throws Exception;
}
