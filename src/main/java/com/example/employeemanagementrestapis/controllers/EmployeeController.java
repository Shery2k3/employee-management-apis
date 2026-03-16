package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.EmployeeDTO;
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
    public ResponseEntity<List<EmployeeDTO.EmployeeResponse>> getAllEmployees() {
        List<EmployeeDTO.EmployeeResponse> response = employeeService.getAllEmployees()
                .stream()
                .map(EmployeeDTO.EmployeeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // POST /api/employee/onboard
    @PostMapping("/onboard")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> onboardEmployee(@RequestBody EmployeeDTO.OnboardRequest request) {
        Employee employee = employeeService.onboardEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeDTO.EmployeeResponse.from(employee));
    }

    // PATCH /api/employee/{id}/assign-department
    @PatchMapping("/{id}/assign-department")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> assignDepartment(
            @PathVariable Long id,
            @RequestBody EmployeeDTO.AssignDepartmentRequest request) {
        Employee employee = employeeService.assignDepartment(id, request.departmentId());
        return ResponseEntity.ok(EmployeeDTO.EmployeeResponse.from(employee));
    }

    // PATCH /api/employee/{id}/assign-manager
    @PatchMapping("/{id}/assign-manager")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> assignManager(
            @PathVariable Long id,
            @RequestBody EmployeeDTO.AssignManagerRequest request) {
        Employee employee = employeeService.assignManager(id, request.managerId());
        return ResponseEntity.ok(EmployeeDTO.EmployeeResponse.from(employee));
    }

    // GET /api/employee/{id}/subordinates
    @GetMapping("/{id}/subordinates")
    public ResponseEntity<List<EmployeeDTO.EmployeeResponse>> getSubordinates(@PathVariable Long id) {
        List<EmployeeDTO.EmployeeResponse> response = employeeService.getSubordinates(id)
                .stream()
                .map(EmployeeDTO.EmployeeResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

}