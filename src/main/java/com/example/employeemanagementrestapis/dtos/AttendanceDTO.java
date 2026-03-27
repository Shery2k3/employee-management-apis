package com.example.employeemanagementrestapis.dtos;

import com.example.employeemanagementrestapis.models.AttendanceRecord;
import com.example.employeemanagementrestapis.models.enums.AttendanceStatus;

import java.time.LocalDateTime;

public class AttendanceDTO {

    public record CheckInRequest (Long employeeId) {}

    public record AttendanceResponse(
            Long id,
            Long employeeId,
            String employeeName,
            LocalDateTime checkInTime,
            LocalDateTime checkOutTime,
            AttendanceStatus status
    ) {
        public static AttendanceResponse from(AttendanceRecord record) {
            return new AttendanceResponse(
                    record.getId(),
                    record.getEmployee().getId(),
                    record.getEmployee().getFirstName() + " " + record.getEmployee().getLastName(),
                    record.getCheckInTime(),
                    record.getCheckOutTime(),
                    record.getStatus()
            );
        }
    }
}
