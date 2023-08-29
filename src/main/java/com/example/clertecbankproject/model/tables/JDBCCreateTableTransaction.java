package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableTransaction {
    public final static String TRANSACTION_TABLE_NAME = "transactions";
    public final static String TABLE_TRANSACTION_SQL_CREATE = "CREATE TABLE if not exists transactions " +
            "(id SERIAL not NULL, " +
            " source_account_id BIGINT, " +
            " target_account_id BIGINT, " +
            " amount NUMERIC(19, 2), " +
            " currency VARCHAR(255), " +
            " transaction_time TIMESTAMP, " +
            " transaction_type VARCHAR(255), " +
            " status VARCHAR(255), " +
            " PRIMARY KEY ( id ))";
}
