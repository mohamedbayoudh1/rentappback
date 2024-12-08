package com.example.banking.controllers;

import com.example.banking.DTO.LoginRequest;
import com.example.banking.entities.User;
import com.example.banking.enums.UserStatus;
import com.example.banking.services.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userServiceImpl.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userServiceImpl.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userServiceImpl.findUserById(id);
    }

    @GetMapping("/mobile/{mobile}")
    public User getUserByMobile(@PathVariable String mobile) {
        return userServiceImpl.findUserByMobile(mobile);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userServiceImpl.findUserByEmail(email);
    }

    @PatchMapping("/{id}/status")
    public User updateUserStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        return userServiceImpl.updateUserStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
    }

    // Verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token) {
        User user = userServiceImpl.findUserByVerificationToken(token);
        if (user == null) {
            return "Invalid verification token.";
        }

        user.setEmailVerified(true);
        user.setStatus(UserStatus.ACTIVE); // Change status to ACTIVE after verification
        userServiceImpl.updateUser(user.getId(), user); // Use the service to update user

        return "Email verified successfully!";

    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        // Find user by email
        User user = userServiceImpl.findUserByEmail(loginRequest.getEmail());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        if (!user.getEmailVerified()) {
            throw new RuntimeException("Email not verified");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("User is not active");
        }

        // Creating authentication token and authenticating the user
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Return a success message or a JWT token if you are using token-based authentication
        return "Login successful, session created.";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Invalidate the session and clear the security context
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

            // Optionally, remove the authentication token or session attributes if using JWT or custom tokens

            return "Logout successful";
        } catch (Exception e) {
            return "Logout failed: " + e.getMessage();
        }
    }
}
