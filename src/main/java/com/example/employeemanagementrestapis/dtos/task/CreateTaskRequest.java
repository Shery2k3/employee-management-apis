package com.example.employeemanagementrestapis.dtos.task;

import com.example.employeemanagementrestapis.models.enums.StoryPoint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank String title,
        String description,
        @NotNull StoryPoint storyPoint,
        LocalDate dueDate
) {
}
