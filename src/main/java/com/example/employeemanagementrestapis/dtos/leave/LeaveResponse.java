package com.example.employeemanagementrestapis.dtos.leave;

import com.example.employeemanagementrestapis.dtos.common.EmployeeSummary;
import com.example.employeemanagementrestapis.models.LeaveRequest;
import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import com.example.employeemanagementrestapis.models.enums.LeaveType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveResponse(
        Long id,
        EmployeeSummary employeeId,
        LeaveType leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveStatus leaveStatus,
        EmployeeSummary reviewerId,
        String reviewerComments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}