package com.example.banking.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class LoanData {
    private String dateFormat = "yyyy-MM-dd"; // df-6
    private String locale = "en_US";
    private String transactionProcessingStrategyCode = "mifos-standard-strategy";
    private int amortizationType = 1;
    private int repaymentEvery = 1;
    private int interestType = 0;
    private int loanTermFrequencyType = 2;
    private int repaymentFrequencyType = 2;
    private String loanType = "individual";
    private int interestCalculationPeriodType = 0;

    private String expectedDisbursementDate;
    private double interestRatePerPeriod;
    private String repaymentsStartingFromDate;

    private String submittedOnDate;
    private int productId;
    private int clientId;
    private int loanTermFrequency;
    private int numberOfRepayments;
    private double principal;

    public LoanData() {
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
