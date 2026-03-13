package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.EmployeeDocument;
import com.example.employeemanagementrestapis.models.enums.DocType;
import com.example.employeemanagementrestapis.repositories.EmployeeDocumentRepository;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class DocumentService {
    private final Path uploadDir = Paths.get("uploads/documents").toAbsolutePath().normalize();

    private final EmployeeRepository employeeRepository;
    private final EmployeeDocumentRepository employeeDocumentRepository;

    public DocumentService(EmployeeRepository employeeRepository, EmployeeDocumentRepository employeeDocumentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeDocumentRepository = employeeDocumentRepository;


        // Creating the upload directory here if it doesnt exist
        try {
            Files.createDirectories(this.uploadDir);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory: " + uploadDir, e);
        }

    }

    @Transactional
    public EmployeeDocument uploadDocument(Long employeeId, MultipartFile file, DocType docType) {

        // Verify that employee actually exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new RuntimeException("File has no name.");
        }

        String sanitizedFileName = StringUtils.cleanPath(originalFilename);

        if (sanitizedFileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence.");
        }

        // Using UUID with original filenames to avoid overwrites
        String uniqueFileName = UUID.randomUUID() + "_" + sanitizedFileName;

        try {
            Path targetLocation = this.uploadDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            EmployeeDocument document = EmployeeDocument.builder().
                    employee(employee).
                    docType(docType)
                    .fileUrl("uploads/documents/" + uniqueFileName)
                    .build();

            return employeeDocumentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + sanitizedFileName);

        }
    }

}
