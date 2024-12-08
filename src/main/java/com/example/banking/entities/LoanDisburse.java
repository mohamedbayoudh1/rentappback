package com.example.banking.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class LoanDisburse {
    private String dateFormat = "yyyy-MM-dd";
    private String locale = "en_US";

    private float transactionAmount;
    private String actualDisbursementDate;

    public LoanDisburse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate today = LocalDate.now();

        this.actualDisbursementDate = today.format(formatter);
    }
}
