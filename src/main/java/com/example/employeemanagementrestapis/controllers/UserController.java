package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/user/
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
