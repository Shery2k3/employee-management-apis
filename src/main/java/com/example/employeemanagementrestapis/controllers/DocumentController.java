package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.document.DocumentResponse;
import com.example.employeemanagementrestapis.models.EmployeeDocument;
import com.example.employeemanagementrestapis.models.enums.DocType;
import com.example.employeemanagementrestapis.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/document")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // POST /api/document/upload
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("docType") DocType docType
    ) {

        EmployeeDocument savedDoc = documentService.uploadDocument(employeeId, file, docType);

        return ResponseEntity.status(HttpStatus.CREATED).body(DocumentResponse.from(savedDoc));
    }

    // GET /api/document/employee/{employeeId}
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByEmployee(
            @PathVariable Long employeeId
    ) {
        List<DocumentResponse> docs = documentService.getDocumentsByEmployeeId(employeeId)
                .stream()
                .map(DocumentResponse::from)
                .toList();

        return ResponseEntity.ok(docs);
    }

    // GET /api/document/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocumentById(
            @PathVariable UUID id
    ) {
        EmployeeDocument doc = documentService.getDocumentById(id);
        return ResponseEntity.ok(DocumentResponse.from(doc));
    }

    // DELETE /api/document/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable UUID id
    ) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
