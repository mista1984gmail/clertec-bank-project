package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;

public interface AccountService {
    void addAccount() throws Exception;
    Account getAccount() throws Exception;
    void depositAccount() throws Exception;
    void deleteAccount () throws Exception;

    void getAllClientAccounts() throws Exception;
}
