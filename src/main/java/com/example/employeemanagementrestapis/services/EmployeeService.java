package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.EmployeeDTO;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthService authService;

    public EmployeeService(EmployeeRepository employeeRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee onboardEmployee(EmployeeDTO.OnboardRequest request) {

        // Insert into User table first
        User user = authService.registerUser(request.email(), request.password());

        Employee employee = Employee.builder()
                .user(user)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .address(request.address())
                .hireDate(request.hireDate())
                .employmentType(request.employmentType())
                .build();

        return employeeRepository.save(employee);

    }
}
