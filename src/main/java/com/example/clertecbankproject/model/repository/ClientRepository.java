package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.Client;

import java.util.List;

public interface ClientRepository {
    boolean saveClient(Client client) throws Exception;
    List<Client> getAllClients() throws Exception;

    Client getClient(Long id) throws Exception;

    void deleteClient(Long id) throws Exception;

    void updateClient(Long id, Client client);

}
