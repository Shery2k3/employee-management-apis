package com.example.employeemanagementrestapis.dtos.auth;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.models.enums.RoleType;

import java.time.LocalDate;

public record UserResponse(Long id, String email, RoleType systemRole, Boolean isActive, LocalDate createdAt) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getSystemRole(), user.getIsActive(), user.getCreatedAt());
    }
}