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

    @GetMapping("/")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/onboard")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> onboardEmployee(@RequestBody EmployeeDTO.OnboardRequest request) {
        Employee employee = employeeService.onboardEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeDTO.EmployeeResponse.from(employee));
    }

}