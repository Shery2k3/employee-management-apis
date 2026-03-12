package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.AuthDTO;
import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthDTO.RegisterUser request) {
        User newUser = authService.registerUser(request.email(), request.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
