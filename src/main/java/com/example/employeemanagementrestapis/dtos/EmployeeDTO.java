package com.example.employeemanagementrestapis.dtos;

import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.enums.EmployeeType;

import java.time.LocalDate;

public class EmployeeDTO {

    public record OnboardRequest (
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String address,
        LocalDate hireDate,
        EmployeeType employmentType
    ) {}

    public record EmployeeResponse(
            Long id,
            String email,
            String firstName,
            String lastName,
            String phone,
            String address,
            LocalDate hireDate,
            EmployeeType employmentType
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
                    employee.getEmploymentType()
            );
        }
    }
}
