package com.example.employeemanagementrestapis.dtos.leave;

import com.example.employeemanagementrestapis.models.enums.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SubmitLeaveRequest(
        @NotNull Long employeeId,
        @NotNull LeaveType leaveType,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotBlank @Size(max = 500) String reason
) {
}