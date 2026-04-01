package com.example.employeemanagementrestapis.dtos;

import com.example.employeemanagementrestapis.models.Asset;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.enums.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AssetDTO {
    public record CreateRequest(
            @NotBlank @Size(max = 120) String name,
            @NotBlank @Size(max = 80) String assetTag,
            @NotNull AssetStatus status,
            Long assignedEmployeeId
    ) {}

    public record UpdateRequest(
            @NotBlank @Size(max = 120) String name,
            @NotNull AssetStatus status,
            Long assignedEmployeeId
    ) {}

    public record EmployeeSummary(Long id, String firstName, String lastName) {}

    public record AssetResponse(
            Long id,
            String name,
            String assetTag,
            AssetStatus status,
            EmployeeSummary assignedEmployee,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    public static AssetResponse fromEntity(Asset asset) {
        Employee employee = asset.getAssignedEmployee();
        EmployeeSummary employeeSummary = employee == null
                ? null
                : new EmployeeSummary(employee.getId(), employee.getFirstName(), employee.getLastName());

        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getAssetTag(),
                asset.getStatus(),
                employeeSummary,
                asset.getCreatedAt(),
                asset.getUpdatedAt()
        );
    }
}

