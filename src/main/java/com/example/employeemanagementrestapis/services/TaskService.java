package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.task.AssignTaskRequest;
import com.example.employeemanagementrestapis.dtos.task.CreateTaskRequest;
import com.example.employeemanagementrestapis.dtos.task.TaskResponse;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.mapper.TaskMapper;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.Task;
import com.example.employeemanagementrestapis.models.TaskAssignment;
import com.example.employeemanagementrestapis.models.enums.TaskStatus;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import com.example.employeemanagementrestapis.repositories.TaskAssignmentRepository;
import com.example.employeemanagementrestapis.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskAssignmentRepository taskAssignmentRepository, EmployeeRepository employeeRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskAssignmentRepository = taskAssignmentRepository;
        this.employeeRepository = employeeRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);
        task.setStatus(TaskStatus.TODO);
        taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    @Transactional
    public void assignTask(AssignTaskRequest request) {
        Task task = taskRepository.findById(request.taskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + request.taskId()));

        Employee assignee = employeeRepository.findById(request.assigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee not found."));

        Employee assignedBy = employeeRepository.findById(request.assignedById())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found."));

        TaskAssignment assignment = TaskAssignment.builder()
                .task(task)
                .assignee(assignee)
                .assignedBy(assignedBy)
                .build();

        taskAssignmentRepository.save(assignment);

        if (task.getStatus() == TaskStatus.TODO)
            task.setStatus(TaskStatus.IN_PROGRESS);

        taskRepository.save(task);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, TaskStatus taskStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        task.setStatus(taskStatus);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskMapper::toResponse);
    }
}
