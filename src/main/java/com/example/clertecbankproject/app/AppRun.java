package com.example.clertecbankproject.app;

import com.example.clertecbankproject.front.AccountFront;
import com.example.clertecbankproject.front.BankFront;
import com.example.clertecbankproject.front.ClientFront;
import com.example.clertecbankproject.model.bd.postgresql.service.BootstrapDataBasePostgreSQL;
import com.example.clertecbankproject.model.bd.postgresql.service.PostgreSQLCreateTables;
import com.example.clertecbankproject.model.repository.*;
import com.example.clertecbankproject.model.repository.impl.*;
import com.example.clertecbankproject.service.*;
import com.example.clertecbankproject.service.impl.*;
import com.example.clertecbankproject.service.schedule.task.InterestCalculationScheduledExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppRun {
    private static final Logger logger = LoggerFactory.getLogger(AppRun.class);
    public static final BankRepository BANK_REPOSITORY = new BankRepositoryImpl();
    public static final TransactionRepository TRANSACTION_REPOSITORY = new TransactionRepositoryImpl();
    public static final BillNumberRepository BILL_NUMBER_REPOSITORY = new BillNumberRepositoryImpl();
    public static final StatementNumberRepository STATEMENT_NUMBER_REPOSITORY = new StatementNumberRepositoryImpl();
    public static final BankService BANK_SERVICE = new BankServiceImpl(BANK_REPOSITORY);
    public static final ClientRepository CLIENT_REPOSITORY = new ClientRepositoryImpl();
    public static final ClientService CLIENT_SERVICE = new ClientServiceImpl(CLIENT_REPOSITORY);
    public static final AccountRepository ACCOUNT_REPOSITORY = new AccountRepositoryImpl();
    public static final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl();
    private static final InterestDateRepository INTEREST_DATE_REPOSITORY = new InterestDateRepositoryImpl();
    public static final PaymentCheckService PAYMENT_CHECK_SERVICE = new PaymentCheckServiceImpl(BANK_REPOSITORY, BILL_NUMBER_REPOSITORY, ACCOUNT_REPOSITORY, CLIENT_REPOSITORY, STATEMENT_NUMBER_REPOSITORY);
    public static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl(ACCOUNT_REPOSITORY);
    public static final ClientFront CLIENT_FRONT = new ClientFront(CLIENT_SERVICE);
    public static final BankFront BANK_FRONT = new BankFront(BANK_SERVICE);
    public static final AccountFront ACCOUNT_FRONT = new AccountFront(ACCOUNT_SERVICE, CLIENT_SERVICE, TRANSACTION_SERVICE, PAYMENT_CHECK_SERVICE);

    public void runApplication() throws Exception {
        //Для создания таблиц
        PostgreSQLCreateTables postgreSQLCreateTables = new PostgreSQLCreateTables();
        postgreSQLCreateTables.createTablesInDataBase();

        //Для заполнения баз данных первоначальными данными
        BootstrapDataBasePostgreSQL bootstrapDataBasePostgreSQL = new BootstrapDataBasePostgreSQL();
        bootstrapDataBasePostgreSQL.fillDataBase();

        //Начисление процентов в последний день месяца
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new InterestCalculationScheduledExecutor(INTEREST_DATE_REPOSITORY, ACCOUNT_REPOSITORY, TRANSACTION_SERVICE, PAYMENT_CHECK_SERVICE), 0, 1, TimeUnit.MINUTES);

        //Консольное приложение
        int userInput = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            logger.info("_______________________________________________________________________");
            logger.info("Главное МЕНЮ:");
            logger.info("---------------------------");
            logger.info("Введите 0 для выхода из приложения");
            logger.info("Введите 1 для входа в МЕНЮ операций над БАНКАМИ");
            logger.info("Введите 2 для входа в МЕНЮ операций над КЛИЕНТАМИ");
            logger.info("Введите 3 для входа в МЕНЮ операций над СЧЕТАМИ");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("До свидания! Хорошего дня!");
                    break;
                case 1:
                    bankOperationMenu(scanner);
                    break;
                case 2:
                    clientOperationMenu(scanner);
                    break;
                case 3:
                    accountOperationMenu(scanner);
                    break;
                default:
                    logger.info("Нет такой операции, пожалуйста, попробуйте другую операцию!");
            }
        }
        while (userInput != 0);
    }

    private static void accountOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("МЕНЮ операций над СЧЕТАМИ:");
            logger.info("---------------------------");
            logger.info("Введите 0 для выхода в Главное МЕНЮ");
            logger.info("Введите 1 для отображения всех счетов клиента");
            logger.info("Введите 2 для отображения счета по id");
            logger.info("Введите 3 для добавления счета");
            logger.info("Введите 4 для пополнения счета");
            logger.info("Введите 5 для перевода денег");
            logger.info("Введите 6 для снятия денег");
            logger.info("Введите 7 для формирования выписки по счету");
            logger.info("Введите 8 для удаления счета");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Возврат в Главное МЕНЮ!");
                    break;
                case 1:
                    ACCOUNT_FRONT.getAllClientAccounts();
                    break;
                case 2:
                    ACCOUNT_FRONT.getAccount();
                    break;
                case 3:
                    ACCOUNT_FRONT.addAccount();
                    break;
                case 4:
                    ACCOUNT_FRONT.depositMoney();
                    break;
                case 5:
                    ACCOUNT_FRONT.replenishmentMoney();
                    break;
                case 6:
                    ACCOUNT_FRONT.withdrawalMoney();
                    break;
                case 7:
                    ACCOUNT_FRONT.generationAccountStatement();
                    break;
                case 8:
                    ACCOUNT_FRONT.deleteAccount();
                    break;
                default:
                    logger.info("Нет такой операции, пожалуйста, попробуйте другую операцию!");
            }
        } while (userInput != 0);
    }

    private static void clientOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("МЕНЮ операций над КЛИЕНТАМИ:");
            logger.info("---------------------------");
            logger.info("Введите 0 для выхода в Главное МЕНЮ");
            logger.info("Введите 1 для отображения всех клиентов");
            logger.info("Введите 2 для отображения клиента по id");
            logger.info("Введите 3 для добавления клиента");
            logger.info("Введите 4 для обновления клиента");
            logger.info("Введите 5 для удаления клиента");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Возврат в Главное МЕНЮ!");
                    break;
                case 1:
                    CLIENT_FRONT.getAllClients();
                    break;
                case 2:
                    CLIENT_FRONT.getClient();
                    break;
                case 3:
                    CLIENT_FRONT.addClient();
                    break;
                case 4:
                    CLIENT_FRONT.updateClient();
                    break;
                case 5:
                    CLIENT_FRONT.deleteClient();
                    break;
                default:
                    logger.info("Нет такой операции, пожалуйста, попробуйте другую операцию!");
            }
        } while (userInput != 0);
    }

    private static void bankOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("МЕНЮ операций над БАНКАМИ:");
            logger.info("---------------------------");
            logger.info("Введите 0 для выхода в Главное МЕНЮ");
            logger.info("Введите 1 для отображения всех банков");
            logger.info("Введите 2 для отображения банка по id");
            logger.info("Введите 3 для добавления банка");
            logger.info("Введите 4 для обновления банка");
            logger.info("Введите 5 для удаления банка");
            logger.info("Введите 6 для добавления клиента в банк");
            logger.info("Введите 7 для отображения всех клиентов банка");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Возврат в Главное МЕНЮ!");
                    break;
                case 1:
                    BANK_FRONT.getAllBanks();
                    break;
                case 2:
                    BANK_FRONT.getBank();
                    break;
                case 3:
                    BANK_FRONT.addBank();
                    break;
                case 4:
                    BANK_FRONT.updateBank();
                    break;
                case 5:
                    BANK_FRONT.deleteBank();
                    break;
                case 6:
                    BANK_FRONT.addClientToBank();
                    break;
                case 7:
                    BANK_FRONT.showAllBankClients();
                    break;
                default:
                    logger.info("Нет такой операции, пожалуйста, попробуйте другую операцию!");
            }
        } while (userInput != 0);
    }
}
