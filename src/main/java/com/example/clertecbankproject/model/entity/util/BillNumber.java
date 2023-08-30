package com.example.clertecbankproject.model.entity.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillNumber {
    private Long id;
    private Long billNumber;

    public BillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }
}
