package com.example.employeemanagementrestapis.dtos.auth;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.models.enums.RoleType;

import java.time.LocalDate;

public record UserResponse(Long id, String email, RoleType systemRole, Boolean isActive, LocalDate createdAt) {
}