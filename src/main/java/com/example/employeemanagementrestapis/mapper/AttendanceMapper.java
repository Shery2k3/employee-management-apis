package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.attendance.AttendanceResponse;
import com.example.employeemanagementrestapis.dtos.attendance.CheckInRequest;
import com.example.employeemanagementrestapis.models.AttendanceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface AttendanceMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(target = "employeeName", expression = "java(record.getEmployee().getFirstName() + \" \" + record.getEmployee().getLastName())")
    AttendanceResponse toResponse(AttendanceRecord record);
}
