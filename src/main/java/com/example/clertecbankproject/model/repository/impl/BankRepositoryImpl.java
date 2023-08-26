package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.repository.BankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankRepositoryImpl implements BankRepository {
    private static final Logger logger = LoggerFactory.getLogger(BankRepositoryImpl.class);

    @Override
    public boolean saveBank(Bank bank) {
        String insertTableSQL = "INSERT INTO banks"
                + "(bank_name) " + "VALUES "
                + "('" + bank.getBankName() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public List<Bank> getAllBanks() throws Exception {
        Connection connection = null;
        List<Bank> banks = new ArrayList<>();
        try {
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            String selectTableSQL = "SELECT * from banks";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("id"));
                String bankName = rs.getString("bank_name");
                Bank bank = new Bank(id, bankName);
                banks.add(bank);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return banks;
    }

    @Override
    public void deleteBank(Long id) throws Exception {
        String deleteTableSQL = "DELETE FROM banks WHERE id = " + id;
        executeStatement(deleteTableSQL);
    }

    @Override
    public Bank getBank(Long id) throws Exception {
        Connection connection = null;
        Bank bank = new Bank();
        try {
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            String selectTableSQL = "SELECT * from banks where id = " + id;
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long bankId = Long.parseLong(rs.getString("id"));
                String bankName = rs.getString("bank_name");
                bank.setId(bankId);
                bank.setBankName(bankName);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return bank;
    }

    @Override
    public void updateBank(Long idForUpdate, String nameOfBank) {
        String updateTableSQL = "UPDATE banks SET bank_name ="
                + "'" + nameOfBank + "'" + "WHERE id = " + idForUpdate;
        executeStatement(updateTableSQL);
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
