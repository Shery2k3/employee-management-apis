package com.example.employeemanagementrestapis.dtos.leave;

import com.example.employeemanagementrestapis.dtos.common.EmployeeSummary;
import com.example.employeemanagementrestapis.models.LeaveRequest;
import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import com.example.employeemanagementrestapis.models.enums.LeaveType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveResponse(
        Long id, EmployeeSummary employee, LeaveType leaveType, LocalDate startDate, LocalDate endDate,
        String reason, LeaveStatus leaveStatus, EmployeeSummary reviewer, String reviewerComments,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static LeaveResponse fromEntity(LeaveRequest req) {
        EmployeeSummary emp = new EmployeeSummary(req.getEmployee().getId(), req.getEmployee().getFirstName(), req.getEmployee().getLastName());
        EmployeeSummary rev = req.getReviewer() == null ? null : new EmployeeSummary(req.getReviewer().getId(), req.getReviewer().getFirstName(), req.getReviewer().getLastName());
        return new LeaveResponse(req.getId(), emp, req.getLeaveType(), req.getStartDate(), req.getEndDate(), req.getReason(), req.getLeaveStatus(), rev, req.getReviewerComments(), req.getCreatedAt(), req.getUpdatedAt());
    }
}