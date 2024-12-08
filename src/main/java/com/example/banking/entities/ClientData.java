package com.example.banking.entities;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ClientData {
    private int officeId = 1;
    private String dateFormat = "dd MMMM yyyy";  // Default value
    private String locale = "en";
    private boolean active = true;
    private int legalFormId = 1;
    private String firstname;
    private String lastname;
    private String externalId;
    private String emailAddress;
    private String mobileNo;
    private String dateOfBirth;
    private String activationDate;
    private String submittedOnDate;
    private String password;
    private String confirmPassword;
    //private boolean isFirstLogin = true;
    private List<Datatable> datatables;
    private Long fineractClientId;

    private int score;
    @Data
    public static class Datatable {
        private String registeredTableName;
        private Map<String, Object> data;
    }


    public ClientData() {
        this.officeId = 1;
        this.dateFormat = "dd MMMM yyyy";
        this.locale = "en";
        this.active = true;
        this.legalFormId = 1;
    }
}