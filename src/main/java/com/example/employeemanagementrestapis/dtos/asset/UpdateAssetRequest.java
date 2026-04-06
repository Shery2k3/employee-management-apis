package com.example.employeemanagementrestapis.dtos.asset;

import com.example.employeemanagementrestapis.models.enums.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAssetRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull AssetStatus status,
        Long assignedEmployeeId
) {
}