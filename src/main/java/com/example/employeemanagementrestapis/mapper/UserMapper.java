package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.auth.UserResponse;
import com.example.employeemanagementrestapis.models.User;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    UserResponse toResponse(User user);
}
