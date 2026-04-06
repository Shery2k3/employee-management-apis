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
    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getUser().getEmail(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhone(),
                employee.getAddress(),
                employee.getHireDate(),
                employee.getEmploymentType(),
                employee.getJobTitle(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getManager() != null ? employee.getManager().getId() : null,
                employee.getManager() != null
                        ? employee.getManager().getFirstName() + " " + employee.getManager().getLastName()
                        : null
        );
    }
}