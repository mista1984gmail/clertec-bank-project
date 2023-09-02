package com.example.clertecbankproject.model.bd.postgresql.service;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Account;
import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.ClientRepository;
import com.example.clertecbankproject.model.repository.impl.AccountRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.BankRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.ClientRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class BootstrapDataBasePostgreSQL {
    private static final Logger logger = LoggerFactory.getLogger(BootstrapDataBasePostgreSQL.class);
    private static Connection connection;
    private static PreparedStatement ps;
    private BankRepository bankRepository = new BankRepositoryImpl();
    private ClientRepository clientRepository = new ClientRepositoryImpl();
    private AccountRepository accountRepository = new AccountRepositoryImpl();
    public void fillDataBase() throws Exception {
        List<Bank> banks = bankRepository.getAllBanks();
        List<Client> clients = clientRepository.getAllClients();
        List<Account> accounts = accountRepository.getAllAccounts();
        if(banks.size() == 0 || clients.size() == 0 || accounts.size() == 0 ){
        connection = JDBCPostgreSQLConnection.getConnection();
        String str = "";
        Scanner scanner = new Scanner(new File("src/main/resources/bootstrap.sql"));
        while(scanner.hasNext())
            str += scanner.nextLine() + "\r\n";
        scanner.close();
        try{
            ps=connection.prepareStatement(str);
            ps.execute();
        }
        catch(SQLException s){
            logger.error(s.getMessage());
        }
        connection.close();}
    }
}
