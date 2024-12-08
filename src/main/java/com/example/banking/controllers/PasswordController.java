package com.example.banking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import java.util.Map;
@RestController
@RequestMapping("/api")
public class PasswordController {

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Simulate email verification logic (replace with actual logic)
        boolean emailExists = verifyEmail(email);

        if (!emailExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email introuvable.");
        }

        // TODO: Send the reset link to the user's email
        System.out.println("Sending reset link to: " + email);

        return ResponseEntity.ok("Reset link sent.");
    }

    private boolean verifyEmail(String email) {
        // Replace with actual logic to check if the email exists in your database
        return email.equals("user@example.com");
    }
}
