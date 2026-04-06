package com.example.employeemanagementrestapis.dtos.employee;

import jakarta.validation.constraints.NotNull;

public record AssignManagerRequest(@NotNull Long managerId) {
}