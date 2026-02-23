package com.flexo.diecut.dto;

import java.time.LocalDate;

public record DieCutResponse(
        Long id,
        String dieNumber,
        Integer repeatTeeth,
        Long projectId,
        String status,
        String storageLocation,
        LocalDate createdDate,
        String notes
) {}
