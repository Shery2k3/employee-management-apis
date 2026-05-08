package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.task.CreateTaskRequest;
import com.example.employeemanagementrestapis.dtos.task.TaskResponse;
import com.example.employeemanagementrestapis.models.Task;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskMapper {

    Task toEntity(CreateTaskRequest request);

    TaskResponse toResponse(Task task);
}
