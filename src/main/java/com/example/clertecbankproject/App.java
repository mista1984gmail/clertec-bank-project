package com.example.clertecbankproject;

import com.example.clertecbankproject.model.repository.BankRepository;
import com.example.clertecbankproject.model.repository.impl.BankRepositoryImpl;
import com.example.clertecbankproject.service.BankService;
import com.example.clertecbankproject.service.impl.BankServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static final BankRepository BANK_REPOSITORY = new BankRepositoryImpl();
    public static final BankService BANK_SERVICE = new BankServiceImpl(BANK_REPOSITORY);

    public static void main(String[] args) throws Exception {
        /*PostgreSQLCreateTables postgreSQLCreateTables = new PostgreSQLCreateTables();
        postgreSQLCreateTables.createTablesInDataBase();*/

        int userInput = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            logger.info("_______________________________________________________________________");
            logger.info("MAIN_MENU:");
            logger.info("---------------------------");
            logger.info("Enter 0 to exit the application");
            logger.info("Enter 1 to BANK_OPERATION_MENU");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("Goodbye!");
                    break;
                case 1:
                    bankOperationMenu(scanner);
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }
        }
        while (userInput != 0);
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
                default:
                    logger.info("There is no such option, please choose another option.");
            }
        } while (userInput != 0);
    }


          /*  do {
            // instructions
            logger.info("Enter 0 to exit the application");//exiting the application
            logger.info("Enter 1 to show all banks");//show all banks
            logger.info("Enter 2 to add bank");//add bank
            logger.info("Enter 3 to delete bank");//delete a bank
            logger.info("Enter 4 to show bank by id");//show bank by id
            logger.info("Enter 4 to show users in the bank");//show users in a bank
            logger.info("Enter 5 to add the user in bank");//add user in a bank
            logger.info("Enter 6 to add user account");//to add user account
            logger.info("Enter 7 to top up account");//top up account
            logger.info("Enter 8 to transfer money");//to transfer money
            logger.info("Enter 9 to show account transactions");//show account transactions
            logger.info("Enter 10 to selection of transactions for the period");//selection of transactions for the period
            System.out.println("_______________________________________________________________________");
            // reading input
            userInput = scanner.nextInt();

            // choosing option
            switch (userInput) {
                case 0:
                    logger.info("Goodbye!");
                    break;
                case 1:
                    BANK_SERVICE.showBanks();
                    break;
                case 2:
                    BANK_SERVICE.addBank();
                    break;
                case 3:
                    BANK_SERVICE.deleteBank();
                    break;
                case 4:
                    BANK_SERVICE.getBank();
                    break;
                case 5:
                    //BANK_SERVICE.addUserInBank();
                    break;
                case 6:
                    //BANK_SERVICE.addUserAccount();
                    break;
                case 7:
                    //BANK_SERVICE.topUpAccount();
                    break;
                case 8:
                    //BANK_SERVICE.transferMoney();
                    break;
                case 9:
                    //BANK_SERVICE.showAccountTransactions();
                    break;
                case 10:
                    //BANK_SERVICE.showAccountTransactionsForPeriod();
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }

        } while (userInput != 0);*/
}
