package com.example.clertecbankproject;

import com.example.clertecbankproject.model.bd.postgresql.service.PostgreSQLCreateTables;
import com.example.clertecbankproject.model.repository.AccountRepository;
import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.ClientRepository;
import com.example.clertecbankproject.model.repository.impl.AccountRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.BankRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.ClientRepositoryImpl;
import com.example.clertecbankproject.service.AccountService;
import com.example.clertecbankproject.service.BankService;
import com.example.clertecbankproject.service.ClientService;
import com.example.clertecbankproject.service.impl.AccountServiceImpl;
import com.example.clertecbankproject.service.impl.BankServiceImpl;
import com.example.clertecbankproject.service.impl.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static final BankRepository BANK_REPOSITORY = new BankRepositoryImpl();
    public static final BankService BANK_SERVICE = new BankServiceImpl(BANK_REPOSITORY);
    public static final ClientRepository CLIENT_REPOSITORY = new ClientRepositoryImpl();
    public static final ClientService CLIENT_SERVICE = new ClientServiceImpl(CLIENT_REPOSITORY);

    public static final AccountRepository ACCOUNT_REPOSITORY = new AccountRepositoryImpl();
    public static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl(ACCOUNT_REPOSITORY);

    public static void main(String[] args) throws Exception {
    /*    PostgreSQLCreateTables postgreSQLCreateTables = new PostgreSQLCreateTables();
        postgreSQLCreateTables.createTablesInDataBase();*/

        int userInput = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            logger.info("_______________________________________________________________________");
            logger.info("MAIN_MENU:");
            logger.info("---------------------------");
            logger.info("Enter 0 to exit the application");
            logger.info("Enter 1 to BANK_OPERATION_MENU");
            logger.info("Enter 2 to CLIENT_OPERATION_MENU");
            logger.info("Enter 3 to ACCOUNT_OPERATION_MENU");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Goodbye!");
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
                    logger.info("There is no such option, please choose another option.");
            }
        }
        while (userInput != 0);
    }

    private static void accountOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("ACCOUNT_OPERATION_MENU:");
            logger.info("---------------------------");
            logger.info("Enter 0 to exit MAIN_MENU");
            logger.info("Enter 1 to show all accounts");
            logger.info("Enter 2 to show account by id");
            logger.info("Enter 3 to add account");
            logger.info("Enter 4 to deposit account");
            logger.info("Enter 5 to delete account");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Exit to MAIN_MENU!");
                    break;
                case 1:
                    ACCOUNT_SERVICE.getAllClientAccounts();
                    break;
                case 2:
                    ACCOUNT_SERVICE.getAccount();
                    break;
                case 3:
                    ACCOUNT_SERVICE.addAccount();
                    break;
                case 4:
                    ACCOUNT_SERVICE.depositAccount();
                    break;
                case 5:
                    ACCOUNT_SERVICE.deleteAccount();
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }
        } while (userInput != 0);
    }

    private static void clientOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("CLIENT_OPERATION_MENU:");
            logger.info("---------------------------");
            logger.info("Enter 0 to exit MAIN_MENU");
            logger.info("Enter 1 to show all clients");
            logger.info("Enter 2 to show client by id");
            logger.info("Enter 3 to add client");
            logger.info("Enter 4 to update client");
            logger.info("Enter 5 to delete client");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Exit to MAIN_MENU!");
                    break;
                case 1:
                    CLIENT_SERVICE.getAllClients();
                    break;
                case 2:
                    CLIENT_SERVICE.getClient();
                    break;
                case 3:
                    CLIENT_SERVICE.addClient();
                    break;
                case 4:
                    CLIENT_SERVICE.updateClient();
                    break;
                case 5:
                    CLIENT_SERVICE.deleteClient();
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }
        } while (userInput != 0);
    }

    private static void bankOperationMenu(Scanner scanner) throws Exception {
        int userInput;
        do {
            logger.info("_______________________________________________________________________");
            logger.info("BANK_OPERATION_MENU:");
            logger.info("---------------------------");
            logger.info("Enter 0 to exit MAIN_MENU");
            logger.info("Enter 1 to show all banks");
            logger.info("Enter 2 to show bank by id");
            logger.info("Enter 3 to add bank");
            logger.info("Enter 4 to update bank");
            logger.info("Enter 5 to delete bank");
            logger.info("Enter 6 to add client to bank");
            logger.info("Enter 7 to show all bank clients");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Exit to MAIN_MENU!");
                    break;
                case 1:
                    BANK_SERVICE.getAllBanks();
                    break;
                case 2:
                    BANK_SERVICE.getBank();
                    break;
                case 3:
                    BANK_SERVICE.addBank();
                    break;
                case 4:
                    BANK_SERVICE.updateBank();
                    break;
                case 5:
                    BANK_SERVICE.deleteBank();
                    break;
                case 6:
                    BANK_SERVICE.addClientToBank();
                    break;
                case 7:
                    BANK_SERVICE.showAllBankClients();
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }
        } while (userInput != 0);
    }
}
