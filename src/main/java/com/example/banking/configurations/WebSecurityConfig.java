package com.example.banking.configurations;


/*
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/users",
                    "/api/client/addClient",
                    "/api/client",
                    "/loan/affectLoan",
                    "/loan/approve/{id}",
                    "/loan/disburse/{id}",
                    "/loan/process"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/client",
                    "/client/clients",
                    "/api/client",
                    "/loan/loans"

            );
            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/api/v1/client",
                    "/api/v1/Loans"
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS
                .and()
                .csrf().disable() // Disable CSRF if necessary
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allow requests from frontend
                        .allowedOrigins("http://localhost:3001")
                        .allowedOrigins("chrome-extension://aicmkgpgakddgnaphhhpliifpcfhicfo") // Allow requests from Postman
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}*/