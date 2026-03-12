package com.example.employeemanagementrestapis.dtos;

public class AuthDTO {

    public record RegisterUser(
            String email,
            String password
    ) {}
}
