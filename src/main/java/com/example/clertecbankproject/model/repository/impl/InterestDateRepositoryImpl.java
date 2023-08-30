package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.entity.util.InterestDate;
import com.example.clertecbankproject.model.repository.InterestDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InterestDateRepositoryImpl implements InterestDateRepository {
    private static final Logger logger = LoggerFactory.getLogger(InterestDateRepositoryImpl.class);

    @Override
    public boolean saveInterestDate(InterestDate interestDate) throws Exception {
        String insertTableSQL = "INSERT INTO interest_date_calculation"
                + "(interest_date) " + "VALUES "
                + "('" + interestDate.getInterestDateOfCalculation() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public List<InterestDate> getAllInterestDate() throws Exception {
        Connection connection = null;
        List<InterestDate> interestDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try {
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            String selectTableSQL = "SELECT * from interest_date_calculation";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = Long.parseLong(rs.getString("id"));
                LocalDateTime interestDateCalculation = LocalDateTime.parse(rs.getString("interest_date"), formatter);
                InterestDate interestDate = new InterestDate(id, interestDateCalculation);
                interestDates.add(interestDate);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return interestDates;
    }



    public void executeStatement(String sql){
        Connection connection = null;
        Statement statement = null;
        try{
            logger.info("Connecting to a database...");
            connection = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    connection.close();
            }catch(SQLException se){
            }
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
}
}
