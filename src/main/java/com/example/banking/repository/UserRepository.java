package com.example.banking.repository;

import com.example.banking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByEmail(String email);

    // Add this method to find a user by verification token
    Optional<User> findByVerificationToken(String token);
}
