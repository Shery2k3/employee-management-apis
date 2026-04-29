package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.leave.LeaveResponse;
import com.example.employeemanagementrestapis.dtos.leave.SubmitLeaveRequest;
import com.example.employeemanagementrestapis.models.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface LeaveMapper {
    @Mapping(source = "employee", target = "employeeId")
    @Mapping(source = "reviewer", target = "reviewerId")
    LeaveResponse toResponse(LeaveRequest leaveRequest);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "reviewer", ignore = true)
//    @Mapping(target = "status", ignore = true)
    LeaveRequest toEntity(SubmitLeaveRequest submitLeaveRequest);
}
