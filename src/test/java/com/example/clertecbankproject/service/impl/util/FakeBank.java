package com.example.clertecbankproject.service.impl.util;

import com.example.clertecbankproject.model.entity.Bank;

public class FakeBank {
    public static Bank getFirstBank(){
        return new Bank(1L,
                "FirstBank");
    }
}
