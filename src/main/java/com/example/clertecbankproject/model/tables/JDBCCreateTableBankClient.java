package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableBankClient {
    public final static String BANK_CLIENT_TABLE_NAME = "bank_clients";
    public final static String TABLE_BANK_CLIENT_SQL_CREATE = "CREATE TABLE if not exists bank_clients " +
            "(id SERIAL not NULL, " +
            " bank_id BIGINT, " +
            " client_id BIGINT, " +
            " PRIMARY KEY ( id ))";
}
