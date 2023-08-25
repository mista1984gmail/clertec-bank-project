package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableClient {
    public final static String ACCOUNT_TABLE_NAME = "clients";
    public final static String TABLE_ACCOUNT_SQL_CREATE = "CREATE TABLE if not exists clients " +
            "(id SERIAL not NULL, " +
            " first_name VARCHAR(255), " +
            " second_name VARCHAR(255), " +
            " last_name VARCHAR(255), " +
            " address VARCHAR(255), " +
            " PRIMARY KEY ( id ))";
}
