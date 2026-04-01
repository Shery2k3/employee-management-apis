package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.LeaveDTO;
import com.example.employeemanagementrestapis.services.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/submit")
    public ResponseEntity<LeaveDTO.LeaveResponse> submitLeave(@Valid @RequestBody LeaveDTO.SubmitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.submitLeaveRequest(request));
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<LeaveDTO.LeaveResponse> reviewLeave(@PathVariable Long id, @Valid @RequestBody LeaveDTO.ReviewRequest request) {
        return ResponseEntity.ok(leaveService.reviewLeaveRequest(id, request));
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<LeaveDTO.LeaveResponse>> getTeamCalendar() {
        return ResponseEntity.ok(leaveService.getAllRequests());
    }
}