package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
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
            connection = JDBCPostgreSQLConnection.getConnection();
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
            logger.error(e.getMessage());
        }
        return banks;
    }

    @Override
    public void deleteBank(Long id) throws Exception {
        Bank bank = getBank(id);
        if(bank.getId()!=null){
            String deleteTableSQL = "DELETE FROM banks WHERE id = " + id;
            executeStatement(deleteTableSQL);
            logger.info("Bank with id= '{}' delete", id);
        }else {
            logger.debug("Bank with id={} don't exist",id);
        }
    }

    @Override
    public Bank getBank(Long id) throws Exception {
        Connection connection = null;
        Bank bank = new Bank();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
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

    @Override
    public void addClientToBank( Long bankId, Long clientId) {
        String insertTableSQL = "INSERT INTO bank_clients"
                + "(bank_id, client_id) " + "VALUES "
                + "(" + bankId + ", "
                + clientId + ")";
        executeStatement(insertTableSQL);

    }

    @Override
    public List<Client> showAllBankClients(Long bankId) throws Exception{
        Connection connection = null;
        List<Client> clients = new ArrayList<>();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT c.id, c.first_name, c.second_name, c.last_name, c.address " +
                    "from clients c " +
                    "left join bank_clients bc " +
                    "on c.id = bc.client_id " +
                    "where bc.bank_id = " + bankId;
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String secondName = rs.getString("second_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                Client client = new Client(id, firstName, secondName, lastName, address);
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return clients;
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
