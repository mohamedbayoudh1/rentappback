package com.example.banking.controllers;

import com.example.banking.DTO.*;
import com.example.banking.entities.*;
import com.example.banking.services.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("loan")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoanRestController {

    private final ILoanService loanService;

    // Fetch loans by client ID
    // Get loans, either all loans or loans filtered by clientId
    @GetMapping("/loans")
    public ResponseEntity<String> getLoans(@RequestParam(required = false) Long clientId) {
        if (clientId != null) {
            return loanService.getLoansByClientId(clientId);
        }
        return loanService.getLoans();
    }

    // Get loans by client ID
    @GetMapping("/loans/client/{clientId}")
    public ResponseEntity<String> getLoansByClientId(@PathVariable Long clientId) {
        return loanService.getLoansByClientId(clientId);
    }
    // Add a new loan
    @PostMapping("/affectLoan")
    public ResponseEntity<String> addLoan(@RequestBody LoanData loanData) {
        return loanService.addLoan(loanData);
    }

    // Approve a loan by its ID
    @PostMapping("/loan/approve/{id}")
    public ResponseEntity<String> approveLoan(@RequestBody ApproveLoan loanData, @PathVariable("id") String id) {
        return loanService.approveLoan(loanData, id);
    }

    // Disburse a loan by its ID
    @PostMapping("/loan/disburse/{id}")
    public ResponseEntity<String> disburseLoan(@RequestBody LoanDisburse loanData, @PathVariable("id") String id) {
        return loanService.disburseLoan(loanData, id);
    }

    // Process a loan with multiple actions
    @PostMapping("/loan/process")
    public ResponseEntity<String> processLoan(@RequestBody LoanProcessData loanData) {
        return loanService.processLoan(loanData);
    }

    // Calculate loan repayment details
    @PostMapping("/loan/calculate-repayment")
    public ResponseEntity<LoanRepaymentResponse> calculateLoanRepayment(@RequestBody LoanRepaymentRequest loanRepaymentRequest) {
        return loanService.calculateLoanRepayment(loanRepaymentRequest);
    }

    // Reject a loan
    @PostMapping("/loan/reject/{id}")
    public ResponseEntity<String> rejectLoan(@RequestBody LoanRejectRequest loanRejectRequest, @PathVariable("id") String id) {
        return loanService.rejectLoan(loanRejectRequest, id);
    }

    // Undo loan approval
    @PostMapping("/loan/undoApproval/{id}")
    public ResponseEntity<String> undoApproval(@RequestBody LoanUndoApprovalRequest loanUndoApprovalRequest, @PathVariable("id") String id) {
        return loanService.undoApproval(loanUndoApprovalRequest, id);
    }

    // Repay a loan by its ID
    @PostMapping("/loan/repay/{loanId}")
    public ResponseEntity<String> repayLoan(@PathVariable("loanId") String loanId, @RequestBody LoanRepaymentTransactionRequest repaymentRequest) {
        return loanService.repayLoan(loanId, repaymentRequest);
    }
}
