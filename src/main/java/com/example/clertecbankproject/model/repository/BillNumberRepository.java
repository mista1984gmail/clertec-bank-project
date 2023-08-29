package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.BillNumber;
import com.example.clertecbankproject.model.entity.Client;

public interface BillNumberRepository {
    boolean saveBillNumber(BillNumber billNumber) throws Exception;
    long getCountBills() throws Exception;
}
