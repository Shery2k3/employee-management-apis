package com.example.employeemanagementrestapis.dtos.asset;

import com.example.employeemanagementrestapis.dtos.common.EmployeeSummary;
import com.example.employeemanagementrestapis.models.Asset;
import com.example.employeemanagementrestapis.models.enums.AssetStatus;

import java.time.LocalDateTime;

public record AssetResponse(
        Long id, String name, String assetTag, AssetStatus status,
        EmployeeSummary assignedEmployee, LocalDateTime createdAt, LocalDateTime updatedAt
) {
}