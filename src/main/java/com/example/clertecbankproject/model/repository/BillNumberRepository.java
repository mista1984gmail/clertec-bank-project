package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.util.BillNumber;

public interface BillNumberRepository {
    boolean saveBillNumber(BillNumber billNumber) throws Exception;
    long getCountBills() throws Exception;
}
