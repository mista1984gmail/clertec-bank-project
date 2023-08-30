package com.example.clertecbankproject.model.repository;

import com.example.clertecbankproject.model.entity.util.InterestDate;

import java.util.List;

public interface InterestDateRepository {
    boolean saveInterestDate(InterestDate interestDate) throws Exception;
    List<InterestDate> getAllInterestDate() throws Exception;
}
