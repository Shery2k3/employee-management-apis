package com.example.employeemanagementrestapis.dtos.department;

import com.example.employeemanagementrestapis.models.Department;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record DepartmentResponse(Long id, String name, int employeeCount) {
    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(department.getId(), department.getName(),
                department.getEmployees() != null ? department.getEmployees().size() : 0);
    }
}