package com.example.clertecbankproject.service.impl.util;

import com.example.clertecbankproject.model.entity.Client;


public class FakeClient {

    public static Client getFirstClient(){
        return new Client(1L,
                "Ivan",
                "Ivanovich",
                "Ivanov",
                "Grodno, st. Pushkina 148/58");
    }
    public static Client getSecondClient(){
        return new Client(2L,
                "Petya",
                "Petrovich",
                "Petrov",
                "Minsk, st. Lermontova 18/14");
    }
}
