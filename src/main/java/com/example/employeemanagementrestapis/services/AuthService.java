package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.models.enums.RoleType;
import com.example.employeemanagementrestapis.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String email, String password) {
        // Duplicate email check
        if (userRepository.findByEmail(email).isPresent())
            throw new BusinessLogicException("User with email: " + email + " already exists.");

        String hashedPassword = passwordEncoder.encode(password);

        // TODO: Use @Builder here later
        User newUser = new User();

        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setSystemRole(RoleType.EMPLOYEE);
        return userRepository.save(newUser);
    }

    // TODO: Handle validations in validators with clean HTTP Responses
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessLogicException("Invalid credentials.");
        }

        return user;
    }
}
