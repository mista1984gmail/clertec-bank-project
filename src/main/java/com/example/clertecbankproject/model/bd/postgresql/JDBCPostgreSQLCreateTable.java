package com.example.clertecbankproject.model.bd.postgresql;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JDBCPostgreSQLCreateTable {
    private static final Logger logger = LoggerFactory.getLogger(JDBCPostgreSQLCreateTable.class);
    public void createTables(Map<String, String> sqls) {
        Connection conn = null;
        Statement stmt = null;
        try{
            logger.info("Connecting to a database...");
            conn = JDBCPostgreSQLConnection.getConnection();
            logger.info("Connected database successfully...");
            stmt = conn.createStatement();
            for(Map.Entry entry: sqls.entrySet()) {
                logger.info("Creating table {} in given database...", entry.getKey());
                stmt.executeUpdate(entry.getValue().toString());
                logger.info("Created table {} in given database...", entry.getKey());
            }
        }catch(SQLException se){
            logger.error(se.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage());
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException sqle){
                logger.error(sqle.getMessage());
            }
        }
    }
}
