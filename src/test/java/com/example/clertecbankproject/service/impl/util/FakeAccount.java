package com.example.clertecbankproject.service.impl.util;

import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FakeAccount {
    public static Account getFirstAccount(){
        return new Account(1L,
                "2K0GF-J7AKP-KCDSM-UF6UR-SQTBL",
                1L,
                1L,
                new BigDecimal(1000.0),
                Currency.BYN,
                LocalDateTime.now());
    }
    public static Account getSecondAccount(){
        return new Account(2L,
                "2NXL0-AFCBG-ISMNO-93FFU-FS62B",
                2L,
                2L,
                new BigDecimal(1000.0),
                Currency.BYN,
                LocalDateTime.now());
    }
}
