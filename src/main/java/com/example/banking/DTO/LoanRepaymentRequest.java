package com.example.banking.DTO;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class LoanRepaymentRequest {
    private String dateFormat = "yyyy-MM-dd";
    private String locale = "en";

    private String loanType = "individual";
    private int loanTermFrequencyType = 2;
    private int repaymentFrequencyType = 2;
    private int repaymentEvery = 1;

    private String principal;
    private int productId;
    private int numberOfRepayments;
    private double interestRatePerPeriod;

    private int amortizationType = 1;
    private int interestType = 0;
    private int interestCalculationPeriodType = 1;

    private String transactionProcessingStrategyCode = "mifos-standard-strategy";
    private int clientId = 3;

    private int loanTermFrequency;

    private String submittedOnDate;
    private String expectedDisbursementDate;


    // initialize dates
    public LoanRepaymentRequest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate today = LocalDate.now();


        this.submittedOnDate = today.format(formatter);

        LocalDate disbursementDate = today.plusMonths(5);
        this.expectedDisbursementDate = disbursementDate.format(formatter);
    }


    public void setNumberOfRepayments(int numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
        this.loanTermFrequency = numberOfRepayments;
    }
}


