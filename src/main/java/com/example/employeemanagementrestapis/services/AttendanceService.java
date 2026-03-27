package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.AttendanceRecord;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.enums.AttendanceStatus;
import com.example.employeemanagementrestapis.repositories.AttendanceRecordRepository;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class AttendanceService {
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final EmployeeRepository employeeRepository;

    // Standard hardcoded business hours
    private static final LocalTime SHIFT_START = LocalTime.of(5, 0);
    private static final LocalTime SHIFT_END = LocalTime.of(17, 0);

    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository, EmployeeRepository employeeRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public AttendanceRecord checkIn(Long employeeId) {
        LocalDate today = LocalDate.now();

        if (attendanceRecordRepository.findByEmployeeIdAndDate(employeeId, today).isPresent()) {
            throw new BusinessLogicException("Employee already checked in today");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        LocalDateTime now = LocalDateTime.now();

        AttendanceStatus status = now.toLocalTime().isAfter(SHIFT_START) ? AttendanceStatus.LATE : AttendanceStatus.PRESENT;

        AttendanceRecord record = AttendanceRecord.builder()
                .employee(employee)
                .date(today)
                .checkInTime(now)
                .status(status)
                .build();

        return attendanceRecordRepository.save(record);
    }

    @Transactional
    public AttendanceRecord checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();

        AttendanceRecord record = attendanceRecordRepository.findByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new ResourceNotFoundException("No check-in record found."));

        if (record.getCheckOutTime() != null) {
            throw new BusinessLogicException("Employee already checked out today.");
        }

        LocalDateTime now = LocalDateTime.now();
        record.setCheckOutTime(now);

        // Flag if checked out early
        if (now.toLocalTime().isBefore(SHIFT_END) && record.getStatus() == AttendanceStatus.PRESENT) {
            record.setStatus(AttendanceStatus.EARLY_DEPARTURE);
        }

        return attendanceRecordRepository.save(record);
    }
}
