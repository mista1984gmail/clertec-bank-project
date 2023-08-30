package com.example.clertecbankproject.model.tables;

public class JDBCCreateTableInterestDateCalculation {
    public final static String INTEREST_DATE_TABLE_NAME = "interest_date_calculation";
    public final static String TABLE_INTEREST_DATE_SQL_CREATE = "CREATE TABLE if not exists interest_date_calculation" +
            "(id SERIAL not NULL, " +
            " interest_date TIMESTAMP, " +
            " PRIMARY KEY ( id ))";
}
