package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.*;
import com.example.clertecbankproject.model.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Transaction> getTransactionsForPeriod(Long id, String startDate, String endDate) throws Exception {
        Connection connection = null;
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        DateTimeFormatter formatterForSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String [] startDateSplit = startDate.split("\\.");
        LocalDate startOfPeriod = LocalDate.of(Integer.parseInt(startDateSplit[2]), Integer.parseInt(startDateSplit[1]), Integer.parseInt(startDateSplit[0]));
        String [] endDateSplit = endDate.split("\\.");
        LocalDate endLocalDate = LocalDate.of(Integer.parseInt(endDateSplit[2]), Integer.parseInt(endDateSplit[1]), Integer.parseInt(endDateSplit[0]));
        LocalDate endOfPeriod = endLocalDate.plusDays(1);
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "select * from transactions t " +
                    "where t.target_account_id = " + id + " " +
                    "and t.status = 'APPROVED' " +
                    "and t.transaction_time between '" + startOfPeriod + "' and '" + endOfPeriod + "'";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long transactionId = Long.parseLong(rs.getString("id"));
                String sourceAccountIdString = rs.getString("source_account_id");
                Long sourceAccountId = null;
                if(sourceAccountIdString!=null){
                    sourceAccountId = Long.parseLong(sourceAccountIdString);
                }
                Long targetAccountId = Long.parseLong(rs.getString("target_account_id"));
                BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(rs.getString("amount")));
                Currency currency = Currency.valueOf(rs.getString("currency"));
                String transactionTimeString = rs.getString("transaction_time");
                String subTransactionTimeString = transactionTimeString.substring(0, 24);
                LocalDateTime transactionTime = LocalDateTime.parse(subTransactionTimeString, formatter);
                TransactionType transactionType = TransactionType.valueOf(rs.getString("transaction_type"));
                Status status = Status.valueOf(rs.getString("status"));
                Transaction transaction = new Transaction(transactionId, sourceAccountId, targetAccountId, amount, currency, transactionTime, transactionType, status);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return transactions;
    }

    public void executeStatement(String sql){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = JDBCPostgreSQLConnection.getConnection();
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
