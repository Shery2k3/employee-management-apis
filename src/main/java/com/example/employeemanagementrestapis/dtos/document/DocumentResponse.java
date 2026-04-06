package com.example.employeemanagementrestapis.dtos.document;
import com.example.employeemanagementrestapis.models.EmployeeDocument;
import com.example.employeemanagementrestapis.models.enums.DocType;
import java.time.LocalDate;
import java.util.UUID;

public record DocumentResponse(UUID id, Long employeeId, DocType docType, String fileUrl, LocalDate uploadedAt) {
    public static DocumentResponse from(EmployeeDocument doc) {
        return new DocumentResponse(doc.getId(), doc.getEmployee().getId(), doc.getDocType(), doc.getFileUrl(), doc.getUploadedAt());
    }
}