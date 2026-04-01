package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    boolean existsByAssetTag(String assetTag);
    List<Asset> findByAssignedEmployeeId(Long employeeId);
}

