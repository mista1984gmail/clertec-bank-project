package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableStatementNumber {
    public final static String STATEMENT_TABLE_NAME = "statement_numbers";
    public final static String TABLE_STATEMENT_SQL_CREATE = "CREATE TABLE if not exists statement_numbers" +
            "(id SERIAL not NULL, " +
            " statement_number BIGINT, " +
            " PRIMARY KEY ( id ))";
}
