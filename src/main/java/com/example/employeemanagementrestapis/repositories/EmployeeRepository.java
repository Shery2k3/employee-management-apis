package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManagerId(Long managerId);
    List<Employee> findByDepartmentId(Long departmentId);
}
