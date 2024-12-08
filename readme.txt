LoanRise Backend
Overview
LoanRise Backend is a Spring Boot application developed for Proxym Enterprise, integrating with the Fineract API to manage loan operations and client data. It provides RESTful endpoints for interacting with Fineract, enabling functionalities such as loan creation, approval, disbursement, and client management.

Features
Loan Management: Create, approve, and disburse loans via REST endpoints.
Client Management: Add and retrieve client data via REST endpoints.
Integration: Communicates with Fineract API using HTTP requests and manages authentication via Basic Auth.
Entities: Includes data transfer objects (DTOs) for loan data, approval details, disbursement information, client details, and processing flows.
Setup
To run this project locally, follow these steps:

Clone Repository: git clone https://github.com/mohamedbayoudh1/LoanRise-backend.git
Navigate to Project Directory: cd LoanRise-backend
Configure Fineract URL:
Update src/main/resources/application.properties with your Fineract API URL.
Ensure the application.properties file has the correct properties for server.port and any other necessary configurations.
Build and Run Application:
Using Maven: mvn spring-boot:run
Or build with Maven and run the JAR file: mvn clean package followed by java -jar target/loanrise-backend.jar
Access Application:
Once running, access the application at http://localhost:8080 (or the configured port).
Usage
REST Endpoints
Loan Operations
GET /loan/loans: Retrieve all loans.
POST /loan/affectLoan: Create a new loan.
POST /loan/loan/approve/{id}: Approve a loan by ID.
POST /loan/loan/disburse/{id}: Disburse a loan by ID.
POST /loan/loan/process: Process a loan (affect, approve, and disburse).
Client Operations
GET /client/clients: Retrieve all clients.
POST /client/addClient: Add a new client.
Example
Create a Loan
http
Copier le code
POST /loan/affectLoan
Content-Type: application/json

{
  "dateFormat": "dd MMMM yyyy",
  "locale": "en",
  "transactionProcessingStrategyCode": "mifos-standard-strategy",
  "amortizationType": 1,
  "repaymentEvery": 1,
  "interestType": 0,
  "loanTermFrequencyType": 2,
  "repaymentFrequencyType": 2,
  "loanType": "individual",
  "interestCalculationPeriodType": 0,
  "expectedDisbursementDate": "30 June 2024",
  "interestRatePerPeriod": 5.5,
  "repaymentsStartingFromDate": null,
  "submittedOnDate": "15 June 2024",
  "productId": 11,
  "clientId": 101,
  "loanTermFrequency": 15,
  "numberOfRepayments": 15,
  "principal": 10000.0
}
Dependencies
Spring Boot: Framework for building web applications.
Lombok: Simplifies boilerplate code with annotations.
Jackson: JSON serialization and deserialization.
RestTemplate: HTTP client for making API requests.
Contributing
Contributions are welcome! Fork the repository and submit a pull request with your improvements.

License
This project is licensed under the MIT License - see the LICENSE file for details.