package com.example.clertecbankproject.model.entity.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementNumber {
    private Long id;
    private Long statementNumber;

    public StatementNumber(Long statementNumber) {
        this.statementNumber = statementNumber;
    }
}
