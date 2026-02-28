package com.flexo.rawmaterial.dto;

import java.sql.Date;

public record RawMaterialResponse(
        Long id,
        Integer widthMm,
        Integer lengthM,
        String batchNumber,
        String supplier,
        Date receivedDate,
        String status,
        String warehouseLocation,
        String assignedMachine
) {}
