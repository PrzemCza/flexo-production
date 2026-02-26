package com.flexo.rawmaterial.dto;

import java.sql.Date;

public record RawMaterialFilter(
        Integer widthMin,
        Integer widthMax,
        Integer lengthMin,
        Integer lengthMax,
        String batchNumber,
        String supplier,
        String status,
        Date receivedDateFrom,
        Date receivedDateTo,
        String warehouseLocation
) {}
