package com.example.banking.DTO;

import lombok.Data;

@Data
public class LoanRepaymentTransactionRequest {
    private String dateFormat = "dd MMMM yyyy";
    private String locale = "en";
    private String transactionDate;
    private String transactionAmount;
    private String note;
    private String paymentTypeId;
    private String accountNumber;
    private String checkNumber;
    private String routingCode;
    private String receiptNumber;
    private String bankNumber;
}
