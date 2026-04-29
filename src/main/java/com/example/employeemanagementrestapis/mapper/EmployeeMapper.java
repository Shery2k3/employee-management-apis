package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.employee.EmployeeResponse;
import com.example.employeemanagementrestapis.dtos.employee.OnboardRequest;
import com.example.employeemanagementrestapis.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface EmployeeMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(target = "managerName", expression = "java(employee.getManager() != null ? employee.getManager().getFirstName() + \" \" + employee.getManager().getLastName() : null)")
    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(OnboardRequest onboardRequest);
}
