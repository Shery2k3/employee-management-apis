package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.task.AssignTaskRequest;
import com.example.employeemanagementrestapis.dtos.task.CreateTaskRequest;
import com.example.employeemanagementrestapis.dtos.task.TaskResponse;
import com.example.employeemanagementrestapis.models.enums.TaskStatus;
import com.example.employeemanagementrestapis.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignTask(@Valid @RequestBody AssignTaskRequest request) {
        taskService.assignTask(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus taskStatus
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, taskStatus));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PagedResponse.fromPage(taskService.getAllTasks(pageable)));
    }

}
