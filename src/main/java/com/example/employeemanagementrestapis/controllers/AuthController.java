package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.auth.AuthRequest;
import com.example.employeemanagementrestapis.dtos.auth.UserResponse;
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

    // POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody AuthRequest request) {
        User newUser = authService.registerUser(request.email(), request.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(newUser));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody AuthRequest request) {
        User user = authService.authenticateUser(request.email(), request.password());

        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(user));
    }

}
