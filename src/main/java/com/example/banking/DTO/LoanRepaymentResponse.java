package com.example.banking.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LoanRepaymentResponse {
    private Currency currency;
    private int loanTermInDays;
    private BigDecimal totalPrincipalDisbursed;
    private BigDecimal totalPrincipalExpected;
    private BigDecimal totalPrincipalPaid;
    private BigDecimal totalInterestCharged;
    private BigDecimal totalFeeChargesCharged;
    private BigDecimal totalPenaltyChargesCharged;
    private BigDecimal totalRepaymentExpected;
    private BigDecimal totalOutstanding;
    private BigDecimal totalCredits;
    private List<Period> periods;

    // Getters and Setters

    // Nested classes for structure
    @Getter
    @Setter
    public static class Currency {
        private String code;
        private String name;
        private int decimalPlaces;
        private int inMultiplesOf;
        private String displaySymbol;
        private String nameCode;
        private String displayLabel;

        public Currency(String code, String name, int decimalPlaces, int inMultiplesOf, String displaySymbol, String nameCode, String displayLabel) {
            this.code = code;
            this.name = name;
            this.decimalPlaces = decimalPlaces;
            this.inMultiplesOf = inMultiplesOf;
            this.displaySymbol = displaySymbol;
            this.nameCode = nameCode;
            this.displayLabel = displayLabel;
        }
    }

    @Getter
    @Setter
    public static class Period {
        private int period;
        private LocalDate fromDate;
        private LocalDate dueDate;
        private int daysInPeriod;
        private BigDecimal principalOriginalDue;
        private BigDecimal principalDue;
        private BigDecimal principalOutstanding;
        private BigDecimal principalLoanBalanceOutstanding;
        private BigDecimal interestOriginalDue;
        private BigDecimal interestDue;
        private BigDecimal interestOutstanding;
        private BigDecimal feeChargesDue;
        private BigDecimal penaltyChargesDue;
        private BigDecimal totalOriginalDueForPeriod;
        private BigDecimal totalDueForPeriod;
        private BigDecimal totalPaidForPeriod;
        private BigDecimal totalOutstandingForPeriod;
        private BigDecimal totalActualCostOfLoanForPeriod;
        private BigDecimal totalInstallmentAmountForPeriod;
        private BigDecimal totalCredits;
        private boolean downPaymentPeriod;

        public Period(int period, LocalDate fromDate, LocalDate dueDate, int daysInPeriod, BigDecimal principalOriginalDue, BigDecimal principalDue, BigDecimal principalOutstanding, BigDecimal principalLoanBalanceOutstanding, BigDecimal interestOriginalDue, BigDecimal interestDue, BigDecimal interestOutstanding, BigDecimal feeChargesDue, BigDecimal penaltyChargesDue, BigDecimal totalOriginalDueForPeriod, BigDecimal totalDueForPeriod, BigDecimal totalPaidForPeriod, BigDecimal totalOutstandingForPeriod, BigDecimal totalActualCostOfLoanForPeriod, BigDecimal totalInstallmentAmountForPeriod, BigDecimal totalCredits, boolean downPaymentPeriod) {
            this.period = period;
            this.fromDate = fromDate;
            this.dueDate = dueDate;
            this.daysInPeriod = daysInPeriod;
            this.principalOriginalDue = principalOriginalDue;
            this.principalDue = principalDue;
            this.principalOutstanding = principalOutstanding;
            this.principalLoanBalanceOutstanding = principalLoanBalanceOutstanding;
            this.interestOriginalDue = interestOriginalDue;
            this.interestDue = interestDue;
            this.interestOutstanding = interestOutstanding;
            this.feeChargesDue = feeChargesDue;
            this.penaltyChargesDue = penaltyChargesDue;
            this.totalOriginalDueForPeriod = totalOriginalDueForPeriod;
            this.totalDueForPeriod = totalDueForPeriod;
            this.totalPaidForPeriod = totalPaidForPeriod;
            this.totalOutstandingForPeriod = totalOutstandingForPeriod;
            this.totalActualCostOfLoanForPeriod = totalActualCostOfLoanForPeriod;
            this.totalInstallmentAmountForPeriod = totalInstallmentAmountForPeriod;
            this.totalCredits = totalCredits;
            this.downPaymentPeriod = downPaymentPeriod;
        }
    }
}
