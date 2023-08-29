package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Currency;
import com.example.clertecbankproject.model.entity.dto.AccountDto;
import com.example.clertecbankproject.model.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private static final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    @Override
    public boolean saveAccount(Account account) throws Exception {
        String insertTableSQL = "INSERT INTO accounts"
                + "(account_number, client_id, bank_id, balance, currency, registration_date) " + "VALUES "
                + "('" + account.getAccountNumber() + "', "
                + account.getClientId() + ", "
                + account.getBankId() + ", "
                + account.getBalance() + ", "
                + "'" + account.getCurrency() + "', "
                + "'" + account.getRegistrationDate() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public Account getAccount(Long id) throws Exception {
        Connection connection = null;
        Account account = new Account();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try {
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            String selectTableSQL = "SELECT * from accounts where id = " + id;
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long accountId = Long.parseLong(rs.getString("id"));
                String accountNumber = rs.getString("account_number");
                Long clientId = Long.parseLong(rs.getString("client_id"));
                Long bankId = Long.parseLong(rs.getString("bank_id"));
                BigDecimal balance = BigDecimal.valueOf(Float.parseFloat(rs.getString("balance")));
                Currency currency = Currency.valueOf(rs.getString("currency"));
                LocalDateTime registrationDate = LocalDateTime.parse(rs.getString("registration_date"), formatter);
                account.setId(accountId);
                account.setAccountNumber(accountNumber);
                account.setClientId(clientId);
                account.setBankId(bankId);
                account.setBalance(balance);
                account.setCurrency(currency);
                account.setRegistrationDate(registrationDate);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return account;
    }

    @Override
    public void depositAccount(Account account, Double deposite) {
        String insertTableSQL = "update accounts a set balance = a.balance + "
                + deposite + "where id = " + account.getId();
        executeStatement(insertTableSQL);
    }

    @Override
    public void deleteAccount(Long id) throws Exception {
        Account account = getAccount(id);
        if(account.getId()!=null){
            String deleteTableSQL = "DELETE FROM accounts WHERE id = " + id;
            executeStatement(deleteTableSQL);
            logger.info("Account with id= '{}' delete", id);
        }else {
            logger.debug("Account with id={} don't exist",id);
        }
    }

    @Override
    public List<AccountDto> getAllClientAccounts(Long idForShow) throws Exception {
        Connection connection = null;
        List<AccountDto> accounts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        try {
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            String selectTableSQL = "select a.id, a.account_number, a.balance, a.currency, a.registration_date, b.bank_name " +
                    "from accounts a, banks b " +
                    "where  a.bank_id = b.id and a.client_id = " + idForShow;
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AccountDto account = new AccountDto();
                Long accountId = Long.parseLong(rs.getString("id"));
                String accountNumber = rs.getString("account_number");
                BigDecimal balance = BigDecimal.valueOf(Float.parseFloat(rs.getString("balance")));
                Currency currency = Currency.valueOf(rs.getString("currency"));
                LocalDateTime registrationDate = LocalDateTime.parse(rs.getString("registration_date"), formatter);
                String bankName = rs.getString("bank_name");

                account.setId(accountId);
                account.setAccountNumber(accountNumber);
                account.setBalance(balance);
                account.setCurrency(currency);
                account.setRegistrationDate(registrationDate);
                account.setBankName(bankName);
                accounts.add(account);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return accounts;
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
