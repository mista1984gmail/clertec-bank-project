package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.ClientRepository;
import com.example.clertecbankproject.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private ClientRepository clientRepository;

    public ClientServiceImpl() {
    }

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(Client client) throws Exception {
        logger.info("Сохранение КЛИЕНТА: {}", client);
        boolean isClientSaved = clientRepository.saveClient(client);
        String success = isClientSaved ? "" : "не";
        logger.info("КЛИЕНТ {} сохранен: {}", success, client);
    }

    @Override
    public List<Client> getAllClients() throws Exception {
        return clientRepository.getAllClients();
    }

    @Override
    public Client getClientById(Long id) throws Exception {
        Client client = clientRepository.getClient(id);
        if (client.getId()==null) {
            logger.debug("КЛИЕНТ с id={} не существует!",id);
        } else {
            logger.debug("КЛИЕНТ с id= '{}', {}", client.getId(), client);
        }
        return client;
    }

    @Override
    public void deleteClient(Long id) throws Exception {
        logger.info("Удаление КЛИЕНТА с id= '{}'", id);
        clientRepository.deleteClient(id);
    }

    @Override
    public void updateClient(Long id, Client client) throws Exception {
        logger.info("Обновление КЛИЕНТА с id= '{}'", id);
        clientRepository.updateClient(id, client);
    }


}
