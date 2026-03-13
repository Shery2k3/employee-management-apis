package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, UUID> {
}
