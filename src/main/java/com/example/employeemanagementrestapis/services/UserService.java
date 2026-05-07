package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.auth.UserResponse;
import com.example.employeemanagementrestapis.mapper.UserMapper;
import com.example.employeemanagementrestapis.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }
}
