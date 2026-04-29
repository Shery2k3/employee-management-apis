package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.document.DocumentResponse;
import com.example.employeemanagementrestapis.models.EmployeeDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface DocumentMapper {
    @Mapping(source = "employee.id", target = "employeeId")
    DocumentResponse toResponse(EmployeeDocument employeeDocument);
}
