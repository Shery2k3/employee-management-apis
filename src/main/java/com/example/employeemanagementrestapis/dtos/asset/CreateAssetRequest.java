package com.example.employeemanagementrestapis.dtos.asset;

import com.example.employeemanagementrestapis.models.enums.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAssetRequest(
        @NotBlank(message = "Name cannot be empty") @Size(max = 120) String name,
        @NotBlank(message = "Asset tag is required") @Size(max = 80) String assetTag,
        @NotNull(message = "Status is required") AssetStatus status,
        Long assignedEmployeeId
) {
}