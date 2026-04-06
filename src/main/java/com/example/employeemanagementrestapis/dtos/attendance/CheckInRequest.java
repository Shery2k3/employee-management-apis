package com.example.employeemanagementrestapis.dtos.attendance;

import jakarta.validation.constraints.NotNull;

public record CheckInRequest(@NotNull(message = "Employee ID is required") Long employeeId) {
}