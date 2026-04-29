package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.department.CreateDepartmentRequest;
import com.example.employeemanagementrestapis.dtos.department.DepartmentResponse;
import com.example.employeemanagementrestapis.models.Department;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface DepartmentMapper {
    DepartmentResponse toResponse(Department department);

    Department toEntity(CreateDepartmentRequest departmentRequest);
}
