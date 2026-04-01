package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.LeaveBalance;
import com.example.employeemanagementrestapis.models.enums.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    Optional<LeaveBalance> findByEmployeeIdAndLeaveType(Long employeeId, LeaveType leaveType);
}
