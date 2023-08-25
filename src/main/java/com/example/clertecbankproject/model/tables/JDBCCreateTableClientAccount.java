package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableClientAccount {
    public final static String CLIENT_ACCOUNT_TABLE_NAME = "client_accounts";
    public final static String TABLE_CLIENT_ACCOUNT_SQL_CREATE = "CREATE TABLE if not exists client_accounts " +
            "(id SERIAL not NULL, " +
            " client_id BIGINT, " +
            " account_id BIGINT, " +
            " PRIMARY KEY ( id ))";
}
