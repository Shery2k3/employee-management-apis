package com.example.employeemanagementrestapis.dtos.employee;

import com.example.employeemanagementrestapis.models.enums.EmployeeType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OnboardRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String phone, String address,
        @NotNull LocalDate hireDate,
        @NotNull EmployeeType employmentType,
        @NotBlank String jobTitle,
        Long departmentId,
        Long managerId
) {
}