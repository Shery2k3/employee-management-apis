package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
}
