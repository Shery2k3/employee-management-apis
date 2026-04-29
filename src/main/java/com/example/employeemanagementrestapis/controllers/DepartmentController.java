package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.department.CreateDepartmentRequest;
import com.example.employeemanagementrestapis.dtos.department.DepartmentResponse;
import com.example.employeemanagementrestapis.dtos.employee.EmployeeResponse;
import com.example.employeemanagementrestapis.mapper.DepartmentMapper;
import com.example.employeemanagementrestapis.models.Department;
import com.example.employeemanagementrestapis.services.DepartmentService;
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
@RequestMapping("/api/department")
@Validated
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.departmentMapper = departmentMapper;
    }

    // POST /api/department/
    @PostMapping("/")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @RequestBody CreateDepartmentRequest request) {

        Department dept = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentMapper.toResponse(dept));
    }

    // GET /api/department/
    @GetMapping("/")
    public ResponseEntity<PagedResponse<DepartmentResponse>> getAllDepartments(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(departmentService.getAllDepartments(pageable));
    }

    // GET /api/department/{id}/employees
    @GetMapping("/{id}/employees")
    public ResponseEntity<PagedResponse<EmployeeResponse>> getEmployeesByDepartment(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        departmentService.getDepartmentById(id);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PagedResponse.fromPage(employeeService.getEmployeesByDepartment(id, pageable)));
    }
}
