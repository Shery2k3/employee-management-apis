package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.employee.OnboardRequest;
import com.example.employeemanagementrestapis.dtos.employee.EmployeeResponse;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.Department;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.repositories.DepartmentRepository;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthService authService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.authService = authService;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(EmployeeResponse::from);
    }

    @Transactional
    public Employee onboardEmployee(OnboardRequest request) {

        // Insert into User table first
        User user = authService.registerUser(request.email(), request.password());

        Department department = null;
        if (request.departmentId() != null) {
            department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.departmentId()));
        }

        Employee manager = null;
        if (request.managerId() != null) {
            manager = employeeRepository.findById(request.managerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.managerId()));
        }

        Employee employee = Employee.builder()
                .user(user)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .address(request.address())
                .hireDate(request.hireDate())
                .employmentType(request.employmentType())
                .jobTitle(request.jobTitle())
                .department(department)
                .manager(manager)
                .build();

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee assignDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (departmentId == null) {
            employee.setDepartment(null);
        } else {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee assignManager(Long employeeId, Long managerId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (managerId == null) {
            employee.setManager(null);
        } else {
            if (employee.getId().equals(managerId)) {
                throw new BusinessLogicException("Cannot assign an employee as their own manager.");
            }

            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + managerId));
            employee.setManager(manager);
        }

        return employeeRepository.save(employee);
    }

    public Page<EmployeeResponse> getSubordinates(Long managerId, Pageable pageable) {
        if (!employeeRepository.existsById(managerId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + managerId);
        }
        return employeeRepository.findByManagerId(managerId, pageable)
                .map(EmployeeResponse::from);
    }

    public Page<EmployeeResponse> getEmployeesByDepartment(Long departmentId, Pageable pageable) {
        return employeeRepository.findByDepartmentId(departmentId, pageable)
                .map(EmployeeResponse::from);
    }

}
