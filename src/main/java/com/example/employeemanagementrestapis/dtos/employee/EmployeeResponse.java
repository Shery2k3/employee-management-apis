package com.example.employeemanagementrestapis.dtos.employee;

import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.enums.EmployeeType;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String address,
        LocalDate hireDate,
        EmployeeType employmentType,
        String jobTitle,
        Long departmentId,
        String departmentName,
        Long managerId,
        String managerName
) {
}