package com.example.banking.services;



import com.example.banking.DTO.*;

import com.example.banking.DTO.LoanRepaymentRequest;
import com.example.banking.DTO.LoanRepaymentResponse;

import com.example.banking.entities.ApproveLoan;
import com.example.banking.entities.LoanData;
import com.example.banking.entities.LoanDisburse;
import com.example.banking.entities.LoanProcessData;
import org.springframework.http.ResponseEntity;

public interface ILoanService {

    ResponseEntity<String> getLoans();

    ResponseEntity<String> addLoan(LoanData loanData);

    ResponseEntity<String> approveLoan(ApproveLoan loanData, String id);

    ResponseEntity<String> disburseLoan(LoanDisburse loanData, String id);

    ResponseEntity<String> processLoan(LoanProcessData loanData);


    ResponseEntity<LoanRepaymentResponse> calculateLoanRepayment(LoanRepaymentRequest loanRepaymentRequest);

    ResponseEntity<String> rejectLoan(LoanRejectRequest loanRejectRequest, String id);

    ResponseEntity<String> undoApproval(LoanUndoApprovalRequest loanUndoApprovalRequest, String id);


    ResponseEntity<String> repayLoan(String loanId, LoanRepaymentTransactionRequest repaymentRequest);


    ResponseEntity<String> getLoansByClientId(Long clientId);
}




