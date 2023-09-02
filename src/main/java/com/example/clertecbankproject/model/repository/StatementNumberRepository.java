package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.util.StatementNumber;

public interface StatementNumberRepository {
    boolean saveStatementNumber(StatementNumber statementNumber) throws Exception;
    long getCountStatement() throws Exception;
}
