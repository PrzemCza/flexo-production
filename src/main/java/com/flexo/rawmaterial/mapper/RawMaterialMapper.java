package com.flexo.rawmaterial.mapper;

import com.flexo.rawmaterial.dto.CreateRawMaterialRequest;
import com.flexo.rawmaterial.dto.RawMaterialResponse;
import com.flexo.rawmaterial.model.RawMaterial;

public class RawMaterialMapper {

    public static RawMaterial toEntity(CreateRawMaterialRequest request) {
        RawMaterial rm = new RawMaterial();
        rm.setWidthMm(request.widthMm());
        rm.setLengthM(request.lengthM());
        rm.setBatchNumber(request.batchNumber());
        rm.setSupplier(request.supplier());
        rm.setReceivedDate(request.receivedDate());
        rm.setStatus(request.status());
        rm.setWarehouseLocation(request.warehouseLocation());
        return rm;
    }

    public static RawMaterialResponse toResponse(RawMaterial rm) {
        return new RawMaterialResponse(
                rm.getId(),
                rm.getWidthMm(),
                rm.getLengthM(),
                rm.getBatchNumber(),
                rm.getSupplier(),
                rm.getReceivedDate(),
                rm.getStatus(),
                rm.getWarehouseLocation()
        );
    }
}
