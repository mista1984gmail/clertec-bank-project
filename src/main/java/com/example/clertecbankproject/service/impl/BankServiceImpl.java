package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Bank;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class BankServiceImpl implements BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
    private BankRepository repository;

    public BankServiceImpl() {
    }

    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addBank(Bank bank) throws Exception {
        logger.info("Сохранение БАНКА: {}", bank.getBankName());
        boolean isBankSaved = repository.saveBank(bank);
        String success = isBankSaved ? "" : "не";
        logger.info("БАНК {} сохранен: {}", success, bank.getBankName());
    }

    @Override
    public List<Bank> getAllBanks() throws Exception {
        return  repository.getAllBanks();

    }

    @Override
    public void deleteBank(Long id) throws Exception {
        logger.info("Удаление БАНКА по id= '{}'", id);
        repository.deleteBank(id);
    }

    @Override
    public Bank getBank(Long id) throws Exception {
        Bank bank = repository.getBank(id);
        if (bank.getId()==null) {
            logger.debug("БАНК с id={} не существует!",id);
        } else {
            logger.debug("БАНК с id= '{}', {}", bank.getId(), bank.getBankName());
        }
        return bank;
    }

    @Override
    public void updateBank(Long id, String nameOfBank){
        repository.updateBank(id, nameOfBank);
    }

    @Override
    public void addClientToBank(Long idBank, Long idClient) {
        repository.addClientToBank(idBank, idClient);
    }

    @Override
    public List<Client> showAllBankClients(Long id) throws Exception{
        return repository.showAllBankClients(id);
    }

}
