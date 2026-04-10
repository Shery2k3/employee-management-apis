package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManagerId(Long managerId);
    Page<Employee> findByManagerId(Long managerId, Pageable pageable);
    List<Employee> findByDepartmentId(Long departmentId);
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
}
