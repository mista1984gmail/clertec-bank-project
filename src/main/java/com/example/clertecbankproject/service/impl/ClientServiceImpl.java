package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.ClientRepository;
import com.example.clertecbankproject.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.function.Consumer;

public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    public static final Consumer<Client> LOG_ACTION = client ->
            logger.debug("{}", client);

    private ClientRepository clientRepository;

    public ClientServiceImpl() {
    }

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient() throws Exception {
        Client client = new Client();
        logger.info("Input information about Client");
        Scanner scanner = new Scanner(System.in);
        logger.info("First name: ");
        String firstName = scanner.nextLine();
        client.setFirstName(firstName);
        logger.info("Second name: ");
        String secondName = scanner.nextLine();
        client.setSecondName(secondName);
        logger.info("Last name: ");
        String lastName = scanner.nextLine();
        client.setLastName(lastName);
        logger.info("Address: ");
        String address = scanner.nextLine();
        client.setAddress(address);
        saveClient(client);
    }

    @Override
    public void getAllClients() throws Exception {
        logger.info("Show all clients");
        clientRepository.getAllClients().forEach(LOG_ACTION);
    }

    @Override
    public Client getClient() throws Exception {
        Long idForShow;
        logger.info("Input id client for show");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Trying to get client with id = '{}'", idForShow);
        Client client = clientRepository.getClient(idForShow);
        if (client.getId()==null) {
            logger.debug("Client with id={} don't exist",idForShow);
        } else {
            logger.debug("Client with id= '{}', {}", client.getId(), client);
        }
        return client;
    }

    @Override
    public void deleteClient() throws Exception {
        Long idForDelete;
        logger.info("Input id client for delete");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        deleteClient(idForDelete);
    }

    @Override
    public void updateClient() throws Exception {
        Long idForUpdate;
        logger.info("Input id client for update");
        Scanner scanner = new Scanner(System.in);
        idForUpdate = scanner.nextLong();
        logger.info("Trying to get bank with id = '{}'", idForUpdate);
        Client client = clientRepository.getClient(idForUpdate);
        if (client.getId()==null) {
            logger.debug("Client with id={} don't exist",idForUpdate);
        } else {
            logger.debug("Client with id= '{}', {} found", client.getId(), client);
            logger.info("Input information about Client");
            Scanner scanner1 = new Scanner(System.in);
            logger.info("First name: ");
            String firstName = scanner1.nextLine();
            if(!firstName.isEmpty()){
                client.setFirstName(firstName);}
            logger.info("Second name: ");
            String secondName = scanner1.nextLine();
            if(!secondName.isEmpty()){
                client.setSecondName(secondName);}
            logger.info("Last name: ");
            String lastName = scanner1.nextLine();
            if(!lastName.isEmpty()){
                client.setLastName(lastName);}
            logger.info("Address: ");
            String address = scanner1.nextLine();
            if(!address.isEmpty()){
                client.setAddress(address);}
            clientRepository.updateClient(idForUpdate, client);
        }
    }

    public void deleteClient(Long id) throws Exception {
        logger.info("Trying to delete client with id= '{}'", id);
        clientRepository.deleteClient(id);
    }

    public void saveClient(Client client) throws Exception {
        logger.info("Trying to save client: {}", client);
        boolean isClientSaved = clientRepository.saveClient(client);
        String success = isClientSaved ? "" : "not ";
        logger.info("Client was {}saved: {}", success, client);
    }
}
