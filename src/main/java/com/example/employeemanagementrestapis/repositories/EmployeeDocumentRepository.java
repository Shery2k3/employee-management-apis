package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.EmployeeDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, UUID> {
    List<EmployeeDocument> findByEmployeeId(Long employeeId);
    Page<EmployeeDocument> findByEmployeeId(Long employeeId, Pageable pageable);
}
