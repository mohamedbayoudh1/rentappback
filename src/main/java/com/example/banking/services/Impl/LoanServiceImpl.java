package com.example.banking.services.Impl;

import com.example.banking.DTO.*;
import com.example.banking.entities.ApproveLoan;
import com.example.banking.entities.LoanData;
import com.example.banking.entities.LoanDisburse;
import com.example.banking.entities.LoanProcessData;
import com.example.banking.services.ILoanService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements ILoanService {

    private static final Logger LOGGER = Logger.getLogger(LoanServiceImpl.class.getName());
    private final RestTemplate restTemplate;
    private final String url = "https://localhost:8443/fineract-provider/api/v1/loans";

    private final String repaymentUrl = url + "?command=calculateLoanSchedule";

    private final String username = "mifos";
    private final String password = "password";
    private final String auth = username + ":" + password;

    private HttpHeaders createHeaders() {
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("Content-Type", "application/json");
        headers.set("Fineract-Platform-TenantId", "default");
        return headers;
    }

    private ResponseEntity<String> sendRequest(String url, Object requestData) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(requestData, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error connecting to Fineract API: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> getLoans() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else {
                LOGGER.log(Level.SEVERE, "Error response from Fineract API: " + response.getBody());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error response from Fineract API: " + response.getBody());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error connecting to Fineract API: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> addLoan(LoanData loanData) {
        return sendRequest(url, loanData);
    }

    @Override
    public ResponseEntity<String> approveLoan(ApproveLoan loanData, String id) {
        String approveUrl = url + "/" + id + "?command=approve";
        return sendRequest(approveUrl, loanData);
    }

    @Override
    public ResponseEntity<String> disburseLoan(LoanDisburse loanData, String id) {
        String disburseUrl = url + "/" + id + "?command=disburse";
        return sendRequest(disburseUrl, loanData);
    }

    @Override
    public ResponseEntity<String> processLoan(LoanProcessData loanData) {
        ResponseEntity<String> affectResponse = addLoan(loanData.getAffectData());
        if (!affectResponse.getStatusCode().is2xxSuccessful()) {
            return affectResponse;
        }

        String affectResponseBody = affectResponse.getBody();  // Parse the loanId from the affectResponse
        String loanId = extractLoanIdFromResponse(affectResponseBody);
        if (loanId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to extract loanId from response");
        }

        ResponseEntity<String> approveResponse = approveLoan(loanData.getApproveData(), loanId); // Step 2: Approve the loan
        if (!approveResponse.getStatusCode().is2xxSuccessful()) {
            return approveResponse;
        }

        return disburseLoan(loanData.getDisburseData(), loanId);  // Step 3: Disburse the loan
    }

    @Override
    public ResponseEntity<LoanRepaymentResponse> calculateLoanRepayment(LoanRepaymentRequest loanRepaymentRequest) {
        String repaymentUrl = url + "?command=calculateLoanSchedule";
        return sendRequest(repaymentUrl, loanRepaymentRequest, LoanRepaymentResponse.class);
    }

    private <T> ResponseEntity<T> sendRequest(String url, Object requestData, Class<T> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(requestData, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> rejectLoan(LoanRejectRequest loanRejectRequest, String id) {
        String rejectUrl = url + "/" + id + "?command=reject";
        return sendRequest(rejectUrl, loanRejectRequest);
    }

    @Override
    public ResponseEntity<String> undoApproval(LoanUndoApprovalRequest loanUndoApprovalRequest, String id) {
        String undoApprovalUrl = url + "/" + id + "?command=undoApproval";
        return sendRequest(undoApprovalUrl, loanUndoApprovalRequest);
    }

    @Override
    public ResponseEntity<String> repayLoan(String loanId, LoanRepaymentTransactionRequest repaymentRequest) {
        String repaymentUrl = url + "/" + loanId + "/transactions?command=repayment";
        return sendRequest(repaymentUrl, repaymentRequest);
    }

    @Override
    public ResponseEntity<String> getLoansByClientId(Long clientId) {
        String clientLoansUrl = url + "?clientId=" + clientId; // Adjust the URL based on your Fineract API
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(clientLoansUrl, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response; // Return the loans data
            } else {
                LOGGER.log(Level.SEVERE, "Error response from Fineract API: " + response.getBody());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error response from Fineract API: " + response.getBody());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to Fineract API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error connecting to Fineract API: " + e.getMessage());
        }
    }

    private String extractLoanIdFromResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
            return node.get("loanId").asText();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing loanId from response", e);
            return null;
        }
    }
}
