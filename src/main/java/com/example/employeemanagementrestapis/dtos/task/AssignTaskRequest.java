package com.example.employeemanagementrestapis.dtos.task;

import jakarta.validation.constraints.NotNull;

public record AssignTaskRequest(
        @NotNull Long taskId,
        @NotNull Long assigneeId,
        @NotNull Long assignedById
) {
}
