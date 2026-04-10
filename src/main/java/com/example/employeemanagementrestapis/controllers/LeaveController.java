package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.leave.LeaveResponse;
import com.example.employeemanagementrestapis.dtos.leave.ReviewLeaveRequest;
import com.example.employeemanagementrestapis.dtos.leave.SubmitLeaveRequest;
import com.example.employeemanagementrestapis.services.LeaveService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave")
@Validated
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/submit")
    public ResponseEntity<LeaveResponse> submitLeave(@Valid @RequestBody SubmitLeaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.submitLeaveRequest(request));
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<LeaveResponse> reviewLeave(@PathVariable Long id, @Valid @RequestBody ReviewLeaveRequest request) {
        return ResponseEntity.ok(leaveService.reviewLeaveRequest(id, request));
    }

    @GetMapping("/calendar")
    public ResponseEntity<PagedResponse<LeaveResponse>> getTeamCalendar(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PagedResponse.fromPage(leaveService.getAllRequests(pageable)));
    }
}