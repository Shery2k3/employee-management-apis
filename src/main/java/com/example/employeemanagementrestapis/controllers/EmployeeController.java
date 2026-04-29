package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.employee.AssignDepartmentRequest;
import com.example.employeemanagementrestapis.dtos.employee.AssignManagerRequest;
import com.example.employeemanagementrestapis.dtos.employee.EmployeeResponse;
import com.example.employeemanagementrestapis.dtos.employee.OnboardRequest;
import com.example.employeemanagementrestapis.mapper.EmployeeMapper;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.services.EmployeeService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    // GET /api/employee/
    @GetMapping("/")
    public ResponseEntity<PagedResponse<EmployeeResponse>> getAllEmployees(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PagedResponse.fromPage(employeeService.getAllEmployees(pageable)));
    }

    // POST /api/employee/onboard
    @PostMapping("/onboard")
    public ResponseEntity<EmployeeResponse> onboardEmployee(@RequestBody OnboardRequest request) {
        Employee employee = employeeService.onboardEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeMapper.toResponse(employee));
    }

    // PATCH /api/employee/{id}/assign-department
    @PatchMapping("/{id}/assign-department")
    public ResponseEntity<EmployeeResponse> assignDepartment(
            @PathVariable Long id,
            @RequestBody AssignDepartmentRequest request) {
        Employee employee = employeeService.assignDepartment(id, request.departmentId());
        return ResponseEntity.ok(employeeMapper.toResponse(employee));
    }

    // PATCH /api/employee/{id}/assign-manager
    @PatchMapping("/{id}/assign-manager")
    public ResponseEntity<EmployeeResponse> assignManager(
            @PathVariable Long id,
            @RequestBody AssignManagerRequest request) {
        Employee employee = employeeService.assignManager(id, request.managerId());
        return ResponseEntity.ok(employeeMapper.toResponse(employee));
    }

    // GET /api/employee/{id}/subordinates
    @GetMapping("/{id}/subordinates")
    public ResponseEntity<PagedResponse<EmployeeResponse>> getSubordinates(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PagedResponse.fromPage(employeeService.getSubordinates(id, pageable)));
    }

}