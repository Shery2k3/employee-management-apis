package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
