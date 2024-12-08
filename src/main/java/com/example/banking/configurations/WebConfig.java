package com.example.banking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow all paths
                .allowedOrigins("http://localhost:3000")  // Allow frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these methods
                .allowedHeaders("*")  // Allow any header
                .allowCredentials(true);  // Allow credentials like cookies or HTTP auth
    }
}
