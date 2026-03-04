package com.flexo.productionmonitor.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjectOrderResponse(
    Long id,
    String orderNumber,
    String jobName,
    String targetMachine,
    String status,
    LocalDate deadline,
    
    // Flagi gotowości
    boolean isRawMaterialReady,
    boolean isDieCutReady,
    boolean isPolymerReady,
    boolean isInksReady,
    
    String inksProgress,
    
    // Zaktualizowane pola tekstowe bazujące na Twoich encjach
    String rawMaterialName, // Tutaj przekażemy Batch Number
    String dieCutName,      // Tutaj Die Number
    String polymerName,     // Tutaj Project ID
    List<String> inkList    // Tutaj Ink Color IDs lub Batch Numbers
) {}