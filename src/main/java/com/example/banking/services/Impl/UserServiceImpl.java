package com.example.banking.services.Impl;

import com.example.banking.DTO.LoginRequest;
import com.example.banking.entities.User;
import com.example.banking.enums.UserStatus;
import com.example.banking.repository.UserRepository;
import com.example.banking.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public User createUser(User user) {
        // Check if email or mobile already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (userRepository.existsByMobile(user.getMobile())) {
            throw new IllegalArgumentException("Mobile number already exists.");
        }

        // Set initial status as PENDING
        user.setStatus(UserStatus.PENDING);

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate a verification token
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        // Save the user to the database
        userRepository.save(user);

        // Send verification email
        String verificationUrl = "http://localhost:8080/users/verify-email?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), verificationUrl);

        return user;
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }
        User existingUser = userOpt.get();

        // Update user fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMobile(updatedUser.getMobile());
        existingUser.setRoles(updatedUser.getRoles());

        return userRepository.save(existingUser);
    }

    public User updateUserStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public User findUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }
    public User findUserByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found.");
        }
        userRepository.deleteById(id);
    }

    // Verify email using token
    public String verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token."));

        user.setEmailVerified(true); // Mark the email as verified
        user.setStatus(UserStatus.ACTIVE); // Set the status to ACTIVE once verified
        user.setVerificationToken(null); // Clear the token as it is no longer needed

        userRepository.save(user);

        return "Email verified successfully!";
    }

    // Password reset logic
    public void sendPasswordResetToken(String email) {
        String token = UUID.randomUUID().toString(); // Generate a token
        System.out.println("Generated Token: " + token + " for email: " + email);
        // TODO: Save token and send email with the reset link
    }

    public void resetPassword(String token, String newPassword) {
        // Example logic for resetting the password
        System.out.println("Password reset for token: " + token);

        // Hash the new password before updating
        String hashedPassword = passwordEncoder.encode(newPassword);
        // TODO: Update the user's password in the database using token logic
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email not verified");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("User is not active");
        }

        return "Login successful";
    }
}
