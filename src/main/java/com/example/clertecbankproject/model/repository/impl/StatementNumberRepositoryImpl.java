package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.util.StatementNumber;
import com.example.clertecbankproject.model.repository.StatementNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class StatementNumberRepositoryImpl implements StatementNumberRepository {
    private static final Logger logger = LoggerFactory.getLogger(StatementNumberRepositoryImpl.class);
    @Override
    public boolean saveStatementNumber(StatementNumber statementNumber) throws Exception {
        String insertTableSQL = "INSERT INTO statement_numbers"
                + "(statement_number) " + "VALUES "
                + "(" + statementNumber.getStatementNumber() + ")";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public long getCountStatement() throws Exception {
        Connection connection = null;
        Long countStatement = 0L;
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT COUNT(*) from statement_numbers";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                countStatement = Long.parseLong(rs.getString("count"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        finally {
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return countStatement;
    }
    public void executeStatement(String sql){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = JDBCPostgreSQLConnection.getConnection();
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
