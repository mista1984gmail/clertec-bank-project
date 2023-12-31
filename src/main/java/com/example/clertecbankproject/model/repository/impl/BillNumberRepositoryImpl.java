package com.example.clertecbankproject.model.repository.impl;

import com.example.clertecbankproject.model.bd.postgresql.JDBCPostgreSQLConnection;
import com.example.clertecbankproject.model.entity.util.BillNumber;
import com.example.clertecbankproject.model.repository.BillNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BillNumberRepositoryImpl implements BillNumberRepository {
    private static final Logger logger = LoggerFactory.getLogger(BillNumberRepositoryImpl.class);

    @Override
    public boolean saveBillNumber(BillNumber billNumber) throws Exception {
        String insertTableSQL = "INSERT INTO bills"
                + "(bill_number) " + "VALUES "
                + "('" + billNumber.getBillNumber() + "')";
        executeStatement(insertTableSQL);
        return true;
    }

    @Override
    public long getCountBills() throws Exception {
        Connection connection = null;
        Long countBills = 0L;
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT COUNT(*) from bills";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                countBills = Long.parseLong(rs.getString("count"));
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
        return countBills;
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
