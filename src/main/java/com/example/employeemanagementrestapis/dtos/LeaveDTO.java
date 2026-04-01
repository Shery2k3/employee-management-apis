package com.example.employeemanagementrestapis.dtos;

import com.example.employeemanagementrestapis.models.LeaveRequest;
import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import com.example.employeemanagementrestapis.models.enums.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveDTO {
    public record SubmitRequest(
            @NotNull Long employeeId,
            @NotNull LeaveType leaveType,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotBlank @Size(max = 500) String reason
    ) {}

    public record ReviewRequest(
            @NotNull Long reviewerId,
            @NotNull LeaveStatus status,
            @Size(max = 500) String comments
    ) {}

    public record LeaveResponse(
            Long id,
            EmployeeSummary employee,
            LeaveType leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String reason,
            LeaveStatus leaveStatus,
            EmployeeSummary reviewer,
            String reviewerComments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    public record EmployeeSummary(Long id, String firstName, String lastName) {}

    public static LeaveResponse fromEntity(LeaveRequest leaveRequest) {
        EmployeeSummary employee = new EmployeeSummary(
                leaveRequest.getEmployee().getId(),
                leaveRequest.getEmployee().getFirstName(),
                leaveRequest.getEmployee().getLastName()
        );

        EmployeeSummary reviewer = leaveRequest.getReviewer() == null
                ? null
                : new EmployeeSummary(
                leaveRequest.getReviewer().getId(),
                leaveRequest.getReviewer().getFirstName(),
                leaveRequest.getReviewer().getLastName()
        );

        return new LeaveResponse(
                leaveRequest.getId(),
                employee,
                leaveRequest.getLeaveType(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getLeaveStatus(),
                reviewer,
                leaveRequest.getReviewerComments(),
                leaveRequest.getCreatedAt(),
                leaveRequest.getUpdatedAt()
        );
    }
}
