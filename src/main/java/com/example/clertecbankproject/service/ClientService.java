package com.example.clertecbankproject.service;

import com.example.clertecbankproject.model.entity.Client;

public interface ClientService {
    void addClient() throws Exception;
    void getAllClients() throws Exception;
    Client getClient() throws Exception;
    void deleteClient () throws Exception;
    void updateClient () throws Exception;

    Client getClientById(Long id) throws Exception;
}
