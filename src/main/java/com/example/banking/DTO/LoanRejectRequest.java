package com.example.banking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRejectRequest {
    private String locale;
    private String dateFormat;
    private String rejectedOnDate;
    private String note;

}
