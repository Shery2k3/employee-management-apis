package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.FileStorageException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.EmployeeDocument;
import com.example.employeemanagementrestapis.models.enums.DocType;
import com.example.employeemanagementrestapis.repositories.EmployeeDocumentRepository;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {
    private final S3Service s3Service;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/jpg",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private final EmployeeRepository employeeRepository;
    private final EmployeeDocumentRepository employeeDocumentRepository;

    public DocumentService(
            EmployeeRepository employeeRepository,
            EmployeeDocumentRepository employeeDocumentRepository, S3Service s3Service) {
        this.employeeRepository = employeeRepository;
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.s3Service = s3Service;
    }

    @Transactional
    public EmployeeDocument uploadDocument(Long employeeId, MultipartFile file, DocType docType) {

        // Verify that employee actually exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        validateFile(file);

        String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());

        // Using UUID with original filenames to avoid overwrites
        String uniqueFileName = UUID.randomUUID() + "_" + sanitizedFileName;

        String fileUrl = s3Service.uploadFile(uniqueFileName, file);

        EmployeeDocument document = EmployeeDocument.builder().
                employee(employee).
                docType(docType)
                .fileUrl(fileUrl)
                .build();

        return employeeDocumentRepository.save(document);
    }

    public Page<EmployeeDocument> getDocumentsByEmployeeId(Long employeeId, Pageable pageable) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        return employeeDocumentRepository.findByEmployeeId(employeeId, pageable);
    }

    public EmployeeDocument getDocumentById(UUID id) {
        return employeeDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
    }

    @Transactional
    public void deleteDocument(UUID id) {
        EmployeeDocument document = employeeDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));

        // Delete from S3
        String fileUrl = document.getFileUrl();
        if (fileUrl != null && fileUrl.contains(".amazonaws.com/")) {
            String fileKey = fileUrl.substring(fileUrl.lastIndexOf(".amazonaws.com/") + 15);
            s3Service.deleteFile(fileKey);
        }

        employeeDocumentRepository.delete(document);
    }

    @Transactional
    public EmployeeDocument updateDocument(UUID id, MultipartFile file, DocType docType) {
        EmployeeDocument document = employeeDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));

        if (docType != null) {
            document.setDocType(docType);
        }

        if (file != null && !file.isEmpty()) {
            validateFile(file);

            // Delete the old file from S3 if it exists
            String oldFileUrl = document.getFileUrl();
            if (oldFileUrl != null && oldFileUrl.contains(".amazonaws.com/")) {
                String oldFileKey = oldFileUrl.substring(oldFileUrl.lastIndexOf(".amazonaws.com/") + 15);
                s3Service.deleteFile(oldFileKey);
            }

            String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "_" + sanitizedFileName;
            String newFileUrl = s3Service.uploadFile(uniqueFileName, file);

            document.setFileUrl(newFileUrl);
        }

        return employeeDocumentRepository.save(document);
    }

    // Private helpers

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessLogicException("File is empty or missing.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessLogicException("File size exceeds the maximum limit of 10MB.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new BusinessLogicException("File type not allowed: " + contentType + ". Allowed Types: PDF, JPEG, PNG, DOC, DOCX");
        }
    }

    private String sanitizeFileName(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BusinessLogicException("File has no name.");
        }
        String sanitized = StringUtils.cleanPath(originalFilename);
        if (sanitized.contains("..")) {
            throw new BusinessLogicException("Filename contains invalid path sequence.");
        }
        return sanitized;
    }

}
