package com.example.employeemanagementrestapis.services;

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
        String hashedPassword = passwordEncoder.encode(password);

        // TODO: Use @Builder here later
        User newUser = new User();

        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setSystemRole(RoleType.EMPLOYEE);
        return userRepository.save(newUser);
    }
}
