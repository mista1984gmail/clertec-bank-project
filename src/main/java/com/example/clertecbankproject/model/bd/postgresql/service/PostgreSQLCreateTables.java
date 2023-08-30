package com.example.clertecbankproject.model.bd.postgresql.service;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLCreateTable;
import com.example.clertecbankproject.model.tables.*;

import java.util.HashMap;
import java.util.Map;

public class PostgreSQLCreateTables {
    private Map<String, String> createTables = new HashMap<>();

    public void createTablesInDataBase(){
        createTables.put(JDBCCreateTableClient.ACCOUNT_TABLE_NAME, JDBCCreateTableClient.TABLE_ACCOUNT_SQL_CREATE);
        createTables.put(JDBCCreateTableAccount.CLIENT_TABLE_NAME, JDBCCreateTableAccount.TABLE_CLIENT_SQL_CREATE);
        createTables.put(JDBCCreateTableClientAccount.CLIENT_ACCOUNT_TABLE_NAME, JDBCCreateTableClientAccount.TABLE_CLIENT_ACCOUNT_SQL_CREATE);
        createTables.put(JDBCCreateTableBank.BANK_TABLE_NAME, JDBCCreateTableBank.TABLE_BANK_SQL_CREATE);
        createTables.put(JDBCCreateTableBankClient.BANK_CLIENT_TABLE_NAME, JDBCCreateTableBankClient.TABLE_BANK_CLIENT_SQL_CREATE);
        createTables.put(JDBCCreateTableTransaction.TRANSACTION_TABLE_NAME, JDBCCreateTableTransaction.TABLE_TRANSACTION_SQL_CREATE);
        createTables.put(JDBCCreateTableBillNumber.BILL_TABLE_NAME, JDBCCreateTableBillNumber.TABLE_BILL_SQL_CREATE);
        createTables.put(JDBCCreateTableInterestDateCalculation.INTEREST_DATE_TABLE_NAME, JDBCCreateTableInterestDateCalculation.TABLE_INTEREST_DATE_SQL_CREATE);
        JDBCPostgreSQLCreateTable jdbcPostgreSQLCreateTable = new JDBCPostgreSQLCreateTable();
        jdbcPostgreSQLCreateTable.createTables(createTables);
    }
}
