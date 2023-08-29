package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableBillNumber {
    public final static String BILL_TABLE_NAME = "bills";
    public final static String TABLE_BILL_SQL_CREATE = "CREATE TABLE if not exists bills" +
            "(id SERIAL not NULL, " +
            " bill_number BIGINT, " +
            " PRIMARY KEY ( id ))";
}
