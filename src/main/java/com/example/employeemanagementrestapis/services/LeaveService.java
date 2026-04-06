package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.leave.LeaveResponse;
import com.example.employeemanagementrestapis.dtos.leave.ReviewLeaveRequest;
import com.example.employeemanagementrestapis.dtos.leave.SubmitLeaveRequest;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.*;
import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import com.example.employeemanagementrestapis.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class LeaveService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceService leaveBalanceService;

    public LeaveService(
            LeaveRequestRepository leaveRequestRepository,
            LeaveBalanceRepository leaveBalanceRepository,
            EmployeeRepository employeeRepository,
            LeaveBalanceService leaveBalanceService
    ) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.employeeRepository = employeeRepository;
        this.leaveBalanceService = leaveBalanceService;
    }

    @Transactional
    public LeaveResponse submitLeaveRequest(SubmitLeaveRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new BusinessLogicException("End date cannot be before start date.");
        }

        if (request.reason().trim().isEmpty()) {
            throw new BusinessLogicException("Reason is required.");
        }

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        boolean hasOverlapping = leaveRequestRepository.existsOverlappingRequest(
                request.employeeId(),
                request.startDate(),
                request.endDate(),
                Set.of(LeaveStatus.PENDING, LeaveStatus.APPROVED)
        );
        if (hasOverlapping) {
            throw new BusinessLogicException("Overlapping leave request already exists for the selected dates.");
        }

        LeaveBalance balance = leaveBalanceService.getOrCreateBalance(employee, request.leaveType());

        long requestedDays = ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;
        if (requestedDays <= 0) {
            throw new BusinessLogicException("Requested days must be at least 1.");
        }

        if (balance.getRemainingDays() < requestedDays) {
            throw new BusinessLogicException("Not enough leave balance. Remaining: " + balance.getRemainingDays());
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .leaveType(request.leaveType())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .reason(request.reason())
                .leaveStatus(LeaveStatus.PENDING)
                .build();

        return LeaveResponse.fromEntity(leaveRequestRepository.save(leaveRequest));
    }

    @Transactional
    public LeaveResponse reviewLeaveRequest(Long requestId, ReviewLeaveRequest request) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
            throw new BusinessLogicException("Only pending leave requests can be reviewed.");
        }

        if (request.status() != LeaveStatus.APPROVED && request.status() != LeaveStatus.REJECTED) {
            throw new BusinessLogicException("Review status must be either APPROVED or REJECTED.");
        }

        Employee reviewer = employeeRepository.findById(request.reviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        Employee manager = leaveRequest.getEmployee().getManager();
        if (manager != null && !manager.getId().equals(reviewer.getId())) {
            throw new BusinessLogicException("Only the assigned manager can review this leave request.");
        }

        leaveRequest.setLeaveStatus(request.status());
        leaveRequest.setReviewer(reviewer);
        leaveRequest.setReviewerComments(request.comments());

        if (request.status() == LeaveStatus.APPROVED) {
            LeaveBalance balance = leaveBalanceService.getOrCreateBalance(
                    leaveRequest.getEmployee(),
                    leaveRequest.getLeaveType()
            );

            long approvedDays = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;
            balance.setUsedDays(balance.getUsedDays() + (int) approvedDays);
            leaveBalanceRepository.save(balance);
        }

        return LeaveResponse.fromEntity(leaveRequestRepository.save(leaveRequest));
    }

    public List<LeaveResponse> getAllRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(LeaveResponse::fromEntity)
                .toList();
    }
}