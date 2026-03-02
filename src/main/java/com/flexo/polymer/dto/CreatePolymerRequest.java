package com.flexo.polymer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePolymerRequest(
        @NotNull(message = "ID projektu jest wymagane")
        Integer projectId,

        @NotNull(message = "Liczba zębów/powtórzeń jest wymagana")
        Integer repeatTeeth,

        @NotNull(message = "Długość jest wymagana")
        Integer lengthMm,

        @NotNull(message = "Szerokość jest wymagana")
        Integer widthMm,

        Integer colorId,

        @NotBlank(message = "Status jest wymagany")
        @Size(max = 50)
        String status, // ACTIVE (Magazyn), INACTIVE (Maszyna)

        @Size(max = 100)
        String storageLocation,

        @Size(max = 500)
        String notes,

        @Size(max = 50)
        String machine
) {}