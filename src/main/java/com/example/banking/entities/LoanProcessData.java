package com.example.banking.entities;

import lombok.Data;

@Data
public class LoanProcessData {
    private LoanData affectData;
    private ApproveLoan approveData;
    private LoanDisburse disburseData;
}
