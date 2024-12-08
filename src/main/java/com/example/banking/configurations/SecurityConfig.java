package com.example.banking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Create and return AuthenticationManager
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password("{noop}password").roles("ADMIN").build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/api/users/login", "/api/users/logout", "/api/users/signup").permitAll() // Replace antMatchers with requestMatchers
                .anyRequest().authenticated() // All other requests require authentication
                .and()
                .formLogin() // Default login page
                .and()
                .logout()
                .logoutUrl("/api/users/logout")  // Define logout URL
                .logoutSuccessUrl("/api/users/login")  // Redirect to login page after logout
                .invalidateHttpSession(true) // Invalidate session after logout
                .clearAuthentication(true) // Clear authentication after logout
                .deleteCookies("JSESSIONID") // Delete session cookies
                .permitAll(); // Allow everyone to access the logout URL

        return http.build();
    }




}
