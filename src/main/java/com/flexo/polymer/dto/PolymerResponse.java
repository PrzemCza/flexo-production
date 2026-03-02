package com.flexo.polymer.dto;

import java.time.LocalDate;

public record PolymerResponse(
    Long id,             // Techniczne ID zawsze Long
    Integer projectId,   // Biznesowe ID jako Integer
    Integer repeatTeeth,
    Integer lengthMm,
    Integer widthMm,
    Integer colorId,
    String status,
    String storageLocation,
    LocalDate createdDate,
    String notes,
    String machine
) {}