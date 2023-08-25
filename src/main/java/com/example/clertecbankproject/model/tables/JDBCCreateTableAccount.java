package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableAccount {
    public final static String CLIENT_TABLE_NAME = "accounts";
    public final static String TABLE_CLIENT_SQL_CREATE = "CREATE TABLE if not exists accounts " +
            "(id SERIAL not NULL, " +
            " account_number VARCHAR(255), " +
            " client_id BIGINT, " +
            " bank_id BIGINT, " +
            " balance NUMERIC(19, 2), " +
            " currency VARCHAR(255), " +
            " registration_date TIMESTAMP, " +
            " PRIMARY KEY ( id ))";
}
