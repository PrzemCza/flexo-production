package com.flexo.ink.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateInkRequest(
    @NotNull(message = "Color ID is required")
    Integer inkColorId,

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    Double quantityKg,

    String storageLocation,

    @NotBlank(message = "Batch number is required")
    String batchNumber,

    @NotNull(message = "Received date is required")
    LocalDate receivedDate,

    @NotBlank(message = "Status is required")
    String status,

    String notes
) {}