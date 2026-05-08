package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
}
