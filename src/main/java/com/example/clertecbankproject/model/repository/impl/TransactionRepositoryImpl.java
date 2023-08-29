package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Transaction;
import com.example.clertecbankproject.model.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRepositoryImpl implements TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepositoryImpl.class);

    @Override
    public boolean saveTransaction(Transaction transaction) throws Exception {
        String insertTableSQL = "INSERT INTO transactions "
                + "(source_account_id, target_account_id, amount, currency, transaction_time, transaction_type, status) " + "VALUES "
                + "(" + transaction.getSourceAccountId() + ", "
                + transaction.getTargetAccountId() + ", "
                + transaction.getAmount() + ", "
                + "'" + transaction.getCurrency() + "', "
                + "'" + transaction.getTransactionTime() + "', "
                + "'" + transaction.getTransactionType() + "',"
                + "'" + transaction.getStatus() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    public void executeStatement(String sql){
        Connection connection = null;
        Statement statement = null;
        try{
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    connection.close();
            }catch(SQLException se){
            }
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
