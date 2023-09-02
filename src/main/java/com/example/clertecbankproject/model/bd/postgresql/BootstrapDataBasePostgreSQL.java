package com.example.clertecbankproject.model.bd.postgresql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class BootstrapDataBasePostgreSQL {
    private static final Logger logger = LoggerFactory.getLogger(BootstrapDataBasePostgreSQL.class);
    private static Connection connection;
    private static PreparedStatement ps;
    public static void fillDataBase() throws ClassNotFoundException, SQLException, IOException {
        connection = JDBCPostgreSQLConnection.getConnection();
        String str = "";
        Scanner scanner = new Scanner(new File("src/main/resources/bootstrap.sql"));
        while(scanner.hasNext())
            str += scanner.nextLine() + "\r\n";
        scanner.close();
        try{
            ps=connection.prepareStatement(str);
            ps.execute();
        }
        catch(SQLException s){
            logger.error(s.getMessage());
        }
        connection.close();
    }
}
