package com.example.banking.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ApproveLoan {
    private String dateFormat = "yyyy-MM-dd";
    private String locale = "en_US";

    private String approvedOnDate;
    private String expectedDisbursementDate;

    private String note;

    public ApproveLoan() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate today = LocalDate.now();

        this.approvedOnDate = today.format(formatter);

        LocalDate disbursementDate = today.plusMonths(5);
        this.expectedDisbursementDate = disbursementDate.format(formatter);
    }
}
