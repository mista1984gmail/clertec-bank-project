package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableBank {
    public final static String BANK_TABLE_NAME = "banks";
    public final static String TABLE_BANK_SQL_CREATE = "CREATE TABLE if not exists banks " +
            "(id SERIAL not NULL, " +
            " bank_name VARCHAR(255), " +
            " PRIMARY KEY ( id ))";
}
