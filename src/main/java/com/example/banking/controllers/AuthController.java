package com.example.banking.controllers;

import com.example.banking.DTO.PasswordResetDTO;
import com.example.banking.DTO.PasswordResetRequestDTO;
import com.example.banking.services.Impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;

    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    // Request Password Reset
    @PostMapping("/password-reset-request")
    public String requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        userServiceImpl.sendPasswordResetToken(request.getEmail());
        return "Password reset link sent.";
    }

    // Reset Password using Token
    @PostMapping("/reset-password/{token}")
    public String resetPassword(
            @PathVariable String token,
            @RequestBody PasswordResetDTO resetDTO) {
        userServiceImpl.resetPassword(token, resetDTO.getNewPassword());
        return "Password successfully reset.";
    }
}