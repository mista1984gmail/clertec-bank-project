package com.example.clertecbankproject;

import com.example.clertecbankproject.model.bd.postgresql.service.PostgreSQLCreateTables;

public class App {
    public static void main(String[] args) {
        PostgreSQLCreateTables postgreSQLCreateTables = new PostgreSQLCreateTables();
        postgreSQLCreateTables.createTablesInDataBase();
    }
}
