package com.example.employeemanagementrestapis.dtos.attendance;

import com.example.employeemanagementrestapis.models.AttendanceRecord;
import com.example.employeemanagementrestapis.models.enums.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceResponse(
        Long id, Long employeeId, String employeeName,
        LocalDateTime checkInTime, LocalDateTime checkOutTime, AttendanceStatus status
) {
    public static AttendanceResponse from(AttendanceRecord record) {
        return new AttendanceResponse(record.getId(), record.getEmployee().getId(),
                record.getEmployee().getFirstName() + " " + record.getEmployee().getLastName(),
                record.getCheckInTime(), record.getCheckOutTime(), record.getStatus());
    }
}