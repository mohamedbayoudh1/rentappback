package com.example.banking.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientRequest {

    String username;
    String password;
    String email;
    String firstname;
    String lastname;
}
