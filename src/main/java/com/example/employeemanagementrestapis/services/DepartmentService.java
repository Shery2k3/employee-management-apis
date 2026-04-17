package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.common.PagedResponse;
import com.example.employeemanagementrestapis.dtos.department.CreateDepartmentRequest;
import com.example.employeemanagementrestapis.dtos.department.DepartmentResponse;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.Department;
import com.example.employeemanagementrestapis.repositories.DepartmentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @CacheEvict(value = {"departments", "department"}, allEntries = true)
    public Department createDepartment(CreateDepartmentRequest request) {
        if (departmentRepository.existsByName(request.name())) {
            throw new BusinessLogicException("Department with name '" + request.name() + "' already exists.");
        }

        Department dept = Department.builder().name(request.name()).build();
        return departmentRepository.save(dept);
    }

    // Redis bucket "departments", which stores the paginated list
    @Cacheable(value = "departments", key = "'page_' + #pageable.pageNumber + 'size_' + #pageable.pageSize")
    public PagedResponse<DepartmentResponse> getAllDepartments(Pageable pageable) {
        Page<DepartmentResponse> page = departmentRepository.findAll(pageable).map(DepartmentResponse::from);
        return PagedResponse.fromPage(page);
    }

    // Redis bucket for individual department fetch
    @Cacheable(value = "department", key = "#id")
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }
}
