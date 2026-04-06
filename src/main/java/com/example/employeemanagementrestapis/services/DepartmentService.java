package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.DepartmentDTO;
import com.example.employeemanagementrestapis.dtos.department.CreateDepartmentRequest;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.Department;
import com.example.employeemanagementrestapis.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(@org.jetbrains.annotations.UnknownNullability CreateDepartmentRequest request) {
        if (departmentRepository.existsByName(request.name())) {
            throw new BusinessLogicException("Department with name '" + request.name() + "' already exists.");
        }

        Department dept = Department.builder().name(request.name()).build();
        return departmentRepository.save(dept);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }
}
