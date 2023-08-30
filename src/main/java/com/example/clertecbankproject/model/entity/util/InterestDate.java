package com.example.clertecbankproject.model.entity.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestDate {
    private Long id;
    private LocalDateTime interestDateOfCalculation;

    public InterestDate(LocalDateTime interestDateOfCalculation) {
        this.interestDateOfCalculation = interestDateOfCalculation;
    }
}
