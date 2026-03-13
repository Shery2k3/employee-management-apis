package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.models.EmployeeDocument;
import com.example.employeemanagementrestapis.models.enums.DocType;
import com.example.employeemanagementrestapis.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/document")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("docType")DocType docType
            ) {

        EmployeeDocument savedDoc = documentService.uploadDocument(employeeId, file, docType);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded document. DB Record ID: " + savedDoc.getId());
    }
}
