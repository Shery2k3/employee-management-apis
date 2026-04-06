package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.attendance.AttendanceResponse;
import com.example.employeemanagementrestapis.dtos.attendance.CheckInRequest;
import com.example.employeemanagementrestapis.models.AttendanceRecord;
import com.example.employeemanagementrestapis.services.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@RequestBody CheckInRequest request) {
        AttendanceRecord record = attendanceService.checkIn(request.employeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AttendanceResponse.from(record));
    }

    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(@RequestBody CheckInRequest request) {
        AttendanceRecord record = attendanceService.checkOut(request.employeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AttendanceResponse.from(record));
    }
}
