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
        logger.info("Введите информацию о КЛИЕНТЕ:");
        Scanner scanner = new Scanner(System.in);
        logger.info("Имя: ");
        String firstName = scanner.nextLine();
        client.setFirstName(firstName);
        logger.info("Отчество: ");
        String secondName = scanner.nextLine();
        client.setSecondName(secondName);
        logger.info("Фамилия: ");
        String lastName = scanner.nextLine();
        client.setLastName(lastName);
        logger.info("Адрес: ");
        String address = scanner.nextLine();
        client.setAddress(address);
        saveClient(client);
    }

    @Override
    public void getAllClients() throws Exception {
        logger.info("Отобразить всех клиентов:");
        clientRepository.getAllClients().forEach(LOG_ACTION);
    }

    @Override
    public Client getClient() throws Exception {
        Long idForShow;
        logger.info("Введите id КЛИЕНТА для отображения:");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Получение КЛИЕНТА по id = '{}'", idForShow);
        Client client = clientRepository.getClient(idForShow);
        if (client.getId()==null) {
            logger.debug("КЛИЕНТ с id={} не существует!",idForShow);
        } else {
            logger.debug("КЛИЕНТ с id= '{}', {}", client.getId(), client);
        }
        return client;
    }

    @Override
    public void deleteClient() throws Exception {
        Long idForDelete;
        logger.info("Введите id КЛИЕНТА для удаления:");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        deleteClient(idForDelete);
    }

    @Override
    public void updateClient() throws Exception {
        Long idForUpdate;
        logger.info("Введите id КЛИЕНТА для обновления");
        Scanner scanner = new Scanner(System.in);
        idForUpdate = scanner.nextLong();
        logger.info("Получение КЛИЕНТА по id = '{}'", idForUpdate);
        Client client = clientRepository.getClient(idForUpdate);
        if (client.getId()==null) {
            logger.debug("КЛИЕНТ с id={} не существует!",idForUpdate);
        } else {
            logger.debug("КЛИЕНТ с id= '{}', {} найден", client.getId(), client);
            logger.info("Введите информацию о КЛИЕНТЕ:");
            Scanner scanner1 = new Scanner(System.in);
            logger.info("Имя: ");
            String firstName = scanner1.nextLine();
            if(!firstName.isEmpty()){
                client.setFirstName(firstName);}
            logger.info("Отчество: ");
            String secondName = scanner1.nextLine();
            if(!secondName.isEmpty()){
                client.setSecondName(secondName);}
            logger.info("Фамилия: ");
            String lastName = scanner1.nextLine();
            if(!lastName.isEmpty()){
                client.setLastName(lastName);}
            logger.info("Адрес: ");
            String address = scanner1.nextLine();
            if(!address.isEmpty()){
                client.setAddress(address);}
            clientRepository.updateClient(idForUpdate, client);
        }
    }

    @Override
    public Client getClientById(Long id) throws Exception {
        return clientRepository.getClient(id);
    }

    public void deleteClient(Long id) throws Exception {
        logger.info("Удаление КЛИЕНТА с id= '{}'", id);
        clientRepository.deleteClient(id);
    }

    public void saveClient(Client client) throws Exception {
        logger.info("Сохранение КЛИЕНТА: {}", client);
        boolean isClientSaved = clientRepository.saveClient(client);
        String success = isClientSaved ? "" : "не";
        logger.info("КЛИЕНТ {} сохранен: {}", success, client);
    }
}
