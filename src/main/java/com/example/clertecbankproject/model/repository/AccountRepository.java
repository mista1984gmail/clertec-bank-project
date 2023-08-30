package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {
    boolean saveAccount(Account account) throws Exception;

    Account getAccount(Long id) throws Exception;

    void depositAccount(Account account, Double deposit);

    void deleteAccount(Long id) throws Exception;

    List<AccountDto> getAllClientAccounts(Long idForShow) throws Exception;
    List<Account> getAllAccounts() throws Exception;
    void setDeposit(Long id, BigDecimal deposit);

}
