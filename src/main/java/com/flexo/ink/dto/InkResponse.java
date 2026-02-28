package com.flexo.ink.dto;

import java.time.LocalDate;

public record InkResponse(
    Long id,
    Integer inkColorId,
    Double quantityKg,
    String storageLocation,
    String batchNumber,
    LocalDate receivedDate,
    String status,
    String notes
) {}