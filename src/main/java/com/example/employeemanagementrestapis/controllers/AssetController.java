package com.example.employeemanagementrestapis.controllers;

import com.example.employeemanagementrestapis.dtos.AssetDTO;
import com.example.employeemanagementrestapis.services.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetDTO.AssetResponse> createAsset(@Valid @RequestBody AssetDTO.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.createAsset(request));
    }

    @GetMapping
    public ResponseEntity<List<AssetDTO.AssetResponse>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO.AssetResponse> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO.AssetResponse> updateAsset(
            @PathVariable Long id,
            @Valid @RequestBody AssetDTO.UpdateRequest request
    ) {
        return ResponseEntity.ok(assetService.updateAsset(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}

