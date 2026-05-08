package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
