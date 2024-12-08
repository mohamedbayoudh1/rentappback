package com.example.banking.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "clients")
public class LocalClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int officeId;
    private String dateFormat;
    private String locale;
    private boolean active;
    private int legalFormId;

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
    private Long fineractClientId;
    private int score;
}
