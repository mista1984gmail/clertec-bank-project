package com.example.clertecbankproject.front;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.function.Consumer;

public class BankFront {
    private static final Logger logger = LoggerFactory.getLogger(BankFront.class);
    public static final Consumer<Bank> LOG_ACTION = bank ->
            logger.debug("id: " + bank.getId() + ", " + bank.getBankName());

    public static final Consumer<Client> LOG_ACTION_CLIENTS = client ->
            logger.debug("{}", client);
    private BankService bankService;

    public BankFront() {
    }

    public BankFront(BankService bankService) {
        this.bankService = bankService;
    }


    public void addBank() throws Exception {
        Bank bank = new Bank();
        logger.info("Введите информацию о БАНКЕ");
        Scanner scanner = new Scanner(System.in);
        logger.info("Наименование БАНКА: ");
        String nameOfBank = scanner.nextLine();
        bank.setBankName(nameOfBank);
        bankService.addBank(bank);
    }


    public void getAllBanks() throws Exception {
        logger.info("Отобразить все банки");
        bankService.getAllBanks().forEach(LOG_ACTION);
    }


    public void deleteBank() throws Exception {
        Long idForDelete;
        logger.info("Введите id БАНКА для удаления:");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextLong();
        bankService.deleteBank(idForDelete);
    }


    public Bank getBank() throws Exception {
        Long idForShow;
        logger.info("Введите id БАНКА для отображения:");
        Scanner scanner = new Scanner(System.in);
        idForShow = scanner.nextLong();
        logger.info("Получение БАНКА по id = '{}'", idForShow);
        Bank bank = bankService.getBank(idForShow);
        return bank;
    }


    public void updateBank() throws Exception {
        Long idForUpdate;
        logger.info("Введите id БАНКА для обновления");
        Scanner scanner = new Scanner(System.in);
        idForUpdate = scanner.nextLong();
        logger.info("Получение БАНКА по id = '{}'", idForUpdate);
        Bank bank = bankService.getBank(idForUpdate);
        if (bank.getId()==null) {
            logger.debug("БАНК с id={} не существует!",idForUpdate);
        } else {
            logger.debug("БАНК с id= '{}', {} найден", bank.getId(), bank.getBankName());
            logger.info("Введите информацию о БАНКЕ");
            logger.info("Наименование БАНКА: ");
            Scanner scanner1 = new Scanner(System.in);
            String nameOfBank = scanner1.nextLine();
            bankService.updateBank(idForUpdate, nameOfBank);
        }
    }


    public void addClientToBank() {
        Long idBank;
        Long idClient;
        logger.info("Введите id БАНКА, в который надо добавить КЛИЕНТА:");
        Scanner scanner = new Scanner(System.in);
        idBank = scanner.nextLong();
        logger.info("Введите  id КЛИЕНТА, которого надо добавить в БАНК: ");
        idClient = scanner.nextLong();
        bankService.addClientToBank(idBank, idClient);
    }


    public void showAllBankClients() throws Exception{
        Long idBank;
        logger.info("Введите id БАНКА, для отображения всех его КЛИЕНТОВ: ");
        Scanner scanner = new Scanner(System.in);
        idBank = scanner.nextLong();
        bankService.showAllBankClients(idBank).forEach(LOG_ACTION_CLIENTS);
    }


}
