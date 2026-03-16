package com.example.employeemanagementrestapis.dtos;

import com.example.employeemanagementrestapis.models.Department;

public class DepartmentDTO {

    public record CreateRequest(
            String name
    ) {}

    public record DepartmentResponse(
            Long id,
            String name,
            int employeeCount
    ) {
        public static DepartmentResponse from(Department department) {
            return new DepartmentResponse(
                    department.getId(),
                    department.getName(),
                    department.getEmployees() != null ? department.getEmployees().size() : 0
            );
        }
    }
}
