package com.example.banking.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directeur")
public class DirecteurController {

    @GetMapping("/dashboard")
    public String getDirecteurDashboard() {
        return "Welcome to Directeur Dashboard!";
    }
}
