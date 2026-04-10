package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.asset.AssetResponse;
import com.example.employeemanagementrestapis.dtos.asset.CreateAssetRequest;
import com.example.employeemanagementrestapis.dtos.asset.UpdateAssetRequest;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.Asset;
import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.enums.AssetStatus;
import com.example.employeemanagementrestapis.repositories.AssetRepository;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetService {
    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;

    public AssetService(AssetRepository assetRepository, EmployeeRepository employeeRepository) {
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public AssetResponse createAsset(CreateAssetRequest request) {
        String assetTag = request.assetTag().trim();
        if (assetRepository.existsByAssetTag(assetTag)) {
            throw new BusinessLogicException("Asset with tag '" + assetTag + "' already exists.");
        }

        Employee assignedEmployee = resolveAssignedEmployee(request.assignedEmployeeId(), request.status());

        Asset asset = Asset.builder()
                .name(request.name().trim())
                .assetTag(assetTag)
                .status(request.status())
                .assignedEmployee(assignedEmployee)
                .build();

        return AssetResponse.fromEntity(assetRepository.save(asset));
    }

    public Page<AssetResponse> getAllAssets(Pageable pageable) {
        return assetRepository.findAll(pageable)
            .map(AssetResponse::fromEntity);
    }

    public AssetResponse getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return AssetResponse.fromEntity(asset);
    }

    @Transactional
    public AssetResponse updateAsset(Long id, UpdateAssetRequest request) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        Employee assignedEmployee = resolveAssignedEmployee(request.assignedEmployeeId(), request.status());

        asset.setName(request.name().trim());
        asset.setStatus(request.status());
        asset.setAssignedEmployee(assignedEmployee);

        return AssetResponse.fromEntity(assetRepository.save(asset));
    }

    @Transactional
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        assetRepository.delete(asset);
    }

    private Employee resolveAssignedEmployee(Long assignedEmployeeId, AssetStatus status) {
        if (status == AssetStatus.ASSIGNED && assignedEmployeeId == null) {
            throw new BusinessLogicException("assignedEmployeeId is required when status is ASSIGNED.");
        }

        if (status != AssetStatus.ASSIGNED && assignedEmployeeId != null) {
            throw new BusinessLogicException("assignedEmployeeId can only be provided when status is ASSIGNED.");
        }

        if (assignedEmployeeId == null) {
            return null;
        }

        return employeeRepository.findById(assignedEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + assignedEmployeeId));
    }
}

