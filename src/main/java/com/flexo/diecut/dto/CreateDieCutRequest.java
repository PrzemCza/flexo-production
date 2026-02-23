package com.flexo.diecut.dto;

public record CreateDieCutRequest(
        String dieNumber,
        Integer repeatTeeth,
        Long projectId,
        String status,
        String storageLocation,
        String notes
) {}
