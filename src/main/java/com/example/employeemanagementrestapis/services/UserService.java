package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
