package com.example.employeemanagementrestapis.dtos.leave;

import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewLeaveRequest(
        @NotNull Long reviewerId,
        @NotNull LeaveStatus status,
        @Size(max = 500) String comments
) {
}