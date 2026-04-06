package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.employee.AssignDepartmentRequest;
import com.example.employeemanagementrestapis.dtos.employee.AssignManagerRequest;
import com.example.employeemanagementrestapis.dtos.employee.EmployeeResponse;
import com.example.employeemanagementrestapis.dtos.employee.OnboardRequest;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employee/
    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> response = employeeService.getAllEmployees()
                .stream()
                .map(EmployeeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // POST /api/employee/onboard
    @PostMapping("/onboard")
    public ResponseEntity<EmployeeResponse> onboardEmployee(@RequestBody OnboardRequest request) {
        Employee employee = employeeService.onboardEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeResponse.from(employee));
    }

    // PATCH /api/employee/{id}/assign-department
    @PatchMapping("/{id}/assign-department")
    public ResponseEntity<EmployeeResponse> assignDepartment(
            @PathVariable Long id,
            @RequestBody AssignDepartmentRequest request) {
        Employee employee = employeeService.assignDepartment(id, request.departmentId());
        return ResponseEntity.ok(EmployeeResponse.from(employee));
    }

    // PATCH /api/employee/{id}/assign-manager
    @PatchMapping("/{id}/assign-manager")
    public ResponseEntity<EmployeeResponse> assignManager(
            @PathVariable Long id,
            @RequestBody AssignManagerRequest request) {
        Employee employee = employeeService.assignManager(id, request.managerId());
        return ResponseEntity.ok(EmployeeResponse.from(employee));
    }

    // GET /api/employee/{id}/subordinates
    @GetMapping("/{id}/subordinates")
    public ResponseEntity<List<EmployeeResponse>> getSubordinates(@PathVariable Long id) {
        List<EmployeeResponse> response = employeeService.getSubordinates(id)
                .stream()
                .map(EmployeeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

}