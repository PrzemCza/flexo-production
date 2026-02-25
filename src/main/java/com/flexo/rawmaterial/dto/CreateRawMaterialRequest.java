package com.flexo.rawmaterial.dto;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRawMaterialRequest(

        @NotNull(message = "Width is required")
        @Min(value = 1, message = "Width must be greater than 0")
        Integer widthMm,

        @NotNull(message = "Length is required")
        @Min(value = 1, message = "Length must be greater than 0")
        Integer lengthM,

        @NotBlank(message = "Batch number is required")
        String batchNumber,

        @NotBlank(message = "Supplier is required")
        String supplier,

        @NotNull(message = "Received date is required")
        Date receivedDate,

        @NotBlank(message = "Status is required")
        String status,

        @NotBlank(message = "Warehouse location is required")
        String warehouseLocation
) {}
