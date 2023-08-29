package com.example.clertecbankproject.service;

import com.example.clertecbankproject.service.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class AccountNumberGenerationService {
    private static final Logger logger = LoggerFactory.getLogger(AccountNumberGenerationService.class);
    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public String generateAccountNumber() {
        String numberAccount = "";
        for (int j = 0; j < 5; j++) {
            char[] text = new char[5];
            for (int i = 0; i <= 4; i++) {
                text[i] = SOURCES.charAt(new Random().nextInt(SOURCES.length()));
            }
            if (j < 4) {
                numberAccount = numberAccount.concat(new String(text) + "-");
            } else {
                numberAccount = numberAccount.concat(new String(text));
            }
        }
        logger.info("Account number has been generated");
        return numberAccount;
    }
}
