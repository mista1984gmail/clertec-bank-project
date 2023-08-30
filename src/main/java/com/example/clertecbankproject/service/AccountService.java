package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Transaction;

public interface AccountService {
    void addAccount() throws Exception;
    Account getAccount() throws Exception;
    Transaction depositMoney() throws Exception;
    void deleteAccount () throws Exception;

    void getAllClientAccounts() throws Exception;

    Transaction replenishmentMoney() throws Exception;
    Transaction withdrawalMoney() throws Exception;
}
