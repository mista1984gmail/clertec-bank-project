package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.dto.AccountDto;

import java.util.List;

public interface AccountService {
    void addAccount(Account account) throws Exception;
    Account getAccount(Long id) throws Exception;
    void depositMoney(Account account, Double amount) throws Exception;
    void deleteAccount (Long id) throws Exception;

    List<AccountDto> getAllClientAccounts(Long id) throws Exception;
}
