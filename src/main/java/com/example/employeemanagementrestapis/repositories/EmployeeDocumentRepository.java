package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {
}
