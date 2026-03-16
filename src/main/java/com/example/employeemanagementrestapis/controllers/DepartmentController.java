package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.DepartmentDTO;
import com.example.employeemanagementrestapis.dtos.EmployeeDTO;
import com.example.employeemanagementrestapis.models.Department;
import com.example.employeemanagementrestapis.services.DepartmentService;
import com.example.employeemanagementrestapis.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    // POST /api/department/
    @PostMapping("/")
    public ResponseEntity<DepartmentDTO.DepartmentResponse> createDepartment(
            @RequestBody DepartmentDTO.CreateRequest request) {

        Department dept = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(DepartmentDTO.DepartmentResponse.from(dept));
    }

    // GET /api/department/
    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO.DepartmentResponse>> getAllDepartments() {
        List<DepartmentDTO.DepartmentResponse> response = departmentService.getAllDepartments()
                .stream()
                .map(DepartmentDTO.DepartmentResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET /api/department/{id}/employees
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDTO.EmployeeResponse>> getEmployeesByDepartment(@PathVariable Long id) {
        departmentService.getDepartmentById(id);
        List<EmployeeDTO.EmployeeResponse> employees = employeeService.getEmployeesByDepartment(id)
                .stream()
                .map(EmployeeDTO.EmployeeResponse::from)
                .toList();
        return ResponseEntity.ok(employees);
    }
}
