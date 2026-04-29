package com.example.employeemanagementrestapis.mapper;

import com.example.employeemanagementrestapis.dtos.asset.AssetResponse;
import com.example.employeemanagementrestapis.dtos.asset.CreateAssetRequest;
import com.example.employeemanagementrestapis.dtos.asset.UpdateAssetRequest;
import com.example.employeemanagementrestapis.models.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class)
public interface AssetMapper {
    AssetResponse toResponse(Asset asset);

    Asset toEntity(CreateAssetRequest createAssetRequest);

    void updateEntityFromRequest(UpdateAssetRequest updateAssetRequest, @MappingTarget Asset asset);
}
