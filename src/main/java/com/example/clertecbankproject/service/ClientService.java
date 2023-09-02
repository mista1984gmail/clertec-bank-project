package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Client;

import java.util.List;

public interface ClientService {
    void addClient(Client client) throws Exception;
    List<Client> getAllClients() throws Exception;
    Client getClientById(Long id) throws Exception;
    void deleteClient (Long id) throws Exception;
    void updateClient (Long id, Client client) throws Exception;
}
