package com.flexo.productionmonitor.dto;

import java.time.LocalDate;

public record ProjectOrderResponse(
    Long id,
    String orderNumber,
    String jobName,
    String targetMachine,
    String status,
    LocalDate deadline,
    
    // Flagi gotowości dla frontendu
    boolean isRawMaterialReady,
    boolean isDieCutReady,
    boolean isPolymerReady,
    boolean isInksReady,
    
    // Informacja o postępie farb (np. "2/5")
    String inksProgress
) {}