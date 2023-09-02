package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    private static final Logger logger = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    @Override
    public boolean saveClient(Client client) throws Exception {
        String insertTableSQL = "INSERT INTO clients"
                + "(first_name, second_name, last_name, address) " + "VALUES "
                + "('" + client.getFirstName() + "', "
                + "'" + client.getSecondName() + "', "
                + "'" + client.getLastName() + "', "
                + "'" + client.getAddress() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public List<Client> getAllClients() throws Exception {
        Connection connection = null;
        List<Client> clients = new ArrayList<>();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT * from clients";
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

    @Override
    public Client getClient(Long id) throws Exception {
        Connection connection = null;
        Client client = new Client();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT * from clients where id = " + id;
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long clientId = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String secondName = rs.getString("second_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                client.setId(clientId);
                client.setFirstName(firstName);
                client.setSecondName(secondName);
                client.setLastName(lastName);
                client.setAddress(address);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return client;
    }

    @Override
    public void deleteClient(Long id) throws Exception {
        Client client = getClient(id);
        if(client.getId()!=null){
            String deleteTableSQL = "DELETE FROM clients WHERE id = " + id;
            executeStatement(deleteTableSQL);
            logger.info("Client with id= '{}' delete", id);
        }else {
            logger.debug("Client with id={} don't exist",id);
        }

    }

    @Override
    public void updateClient(Long id, Client client) {
        String updateTableSQL = "UPDATE clients SET "
                + "first_name =" + "'" + client.getFirstName() + "',"
                + "second_name =" + "'" + client.getSecondName() + "',"
                + "last_name =" + "'" + client.getLastName() + "',"
                + "address =" + "'" + client.getAddress() + "'"
                + "WHERE id = " + id;
        executeStatement(updateTableSQL);
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
