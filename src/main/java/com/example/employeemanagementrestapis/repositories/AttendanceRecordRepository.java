package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
