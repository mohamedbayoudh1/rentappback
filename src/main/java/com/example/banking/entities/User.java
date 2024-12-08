package com.example.banking.entities;

import com.example.banking.enums.Role;
import com.example.banking.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String mobile;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // User status (ACTIVE, INACTIVE, PENDING)


    private String verificationToken; // Store the verification token
    private boolean emailVerified = false; // Track if the email is verified
  

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles; // Set to handle multiple roles for a single user

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public boolean isEmailVerified() {return emailVerified;
    }
}
