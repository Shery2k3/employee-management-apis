package com.example.employeemanagementrestapis.dtos.attendance;

import com.example.employeemanagementrestapis.models.AttendanceRecord;
import com.example.employeemanagementrestapis.models.enums.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceResponse(Long id, Long employeeId, String employeeName, LocalDateTime checkInTime,
                                 LocalDateTime checkOutTime, AttendanceStatus status) {
}