package com.example.employeemanagementrestapis.dtos.department;

import com.example.employeemanagementrestapis.models.Department;

public record DepartmentResponse(Long id, String name, int employeeCount) {
}