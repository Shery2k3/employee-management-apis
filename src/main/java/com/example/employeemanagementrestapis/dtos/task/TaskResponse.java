package com.example.employeemanagementrestapis.dtos.task;

import com.example.employeemanagementrestapis.models.enums.StoryPoint;
import com.example.employeemanagementrestapis.models.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        StoryPoint storyPoint,
        TaskStatus status,
        LocalDate dueDate
) {
}
