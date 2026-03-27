package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
