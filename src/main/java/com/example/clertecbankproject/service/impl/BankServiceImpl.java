package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.function.Consumer;

public class BankServiceImpl implements BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
    public static final Consumer<Bank> LOG_ACTION = bank ->
            logger.debug("id: " + bank.getId() + ", " + bank.getBankName());

    public static final Consumer<Client> LOG_ACTION_CLIENTS = client ->
            logger.debug("{}", client);
    private BankRepository repository;

    public BankServiceImpl() {
    }

    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addBank() throws Exception {
        Bank bank = new Bank();
        logger.info("Input information about Bank");
        Scanner scanner = new Scanner(System.in);
        logger.info("Name: ");
        String nameOfBank = scanner.nextLine();
        bank.setBankName(nameOfBank);
        saveBank(bank);
    }

    @Override
    public void getAllBanks() throws Exception {
        logger.info("Show all banks");
        repository.getAllBanks().forEach(LOG_ACTION);

    }

    @Override
    public void deleteBank() throws Exception {
        Long idForDelete;
        logger.info("Input id bank for delete");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        deleteBank(idForDelete);
    }

    @Override
    public Bank getBank() throws Exception {
        Long idForShow;
        logger.info("Input id bank for show");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Trying to get bank with id = '{}'", idForShow);
        Bank bank = repository.getBank(idForShow);
        if (bank.getId()==null) {
            logger.debug("Bank with id={} don't exist",idForShow);
        } else {
            logger.debug("Bank with id= '{}', {}", bank.getId(), bank.getBankName());
        }
        return bank;
    }

    @Override
    public void updateBank() throws Exception {
        Long idForUpdate;
        logger.info("Input id bank for update");
        Scanner scanner = new Scanner(System.in);
        idForUpdate = scanner.nextLong();
        logger.info("Trying to get bank with id = '{}'", idForUpdate);
        Bank bank = repository.getBank(idForUpdate);
        if (bank.getId()==null) {
            logger.debug("Bank with id={} don't exist",idForUpdate);
        } else {
            logger.debug("Bank with id= '{}', {} found", bank.getId(), bank.getBankName());
            logger.info("Input information about Bank");
            logger.info("Name: ");
            Scanner scanner1 = new Scanner(System.in);
            String nameOfBank = scanner1.nextLine();
            repository.updateBank(idForUpdate, nameOfBank);
        }
    }

    @Override
    public void addClientToBank() {
        Long idBank;
        Long idClient;
        logger.info("Enter the id of the bank to which you want to add a client: ");
        Scanner scanner = new Scanner(System.in);
        idBank = scanner.nextLong();
        logger.info("Enter the id of the client to be added: ");
        idClient = scanner.nextLong();
        repository.addClientToBank(idBank, idClient);
    }

    @Override
    public void showAllBankClients() throws Exception{
        Long idBank;
        logger.info("Enter the id of the bank to which you want to show all clients: ");
        Scanner scanner = new Scanner(System.in);
        idBank = scanner.nextLong();
        repository.showAllBankClients(idBank).forEach(LOG_ACTION_CLIENTS);
    }

    public void deleteBank(Long id) throws Exception {
        logger.info("Trying to delete bank with id= '{}'", id);
        repository.deleteBank(id);
    }

    public void saveBank(Bank bank) throws Exception {
        logger.info("Trying to save bank: {}", bank.getBankName());
        boolean isBankSaved = repository.saveBank(bank);
        String success = isBankSaved ? "" : "not ";
        logger.info("Bank was {}saved: {}", success, bank.getBankName());
    }
}
