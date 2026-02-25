package com.flexo.diecut.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDieCutRequest(

        @NotBlank(message = "Numer wykrojnika nie może być pusty")
        @Size(max = 100, message = "Numer wykrojnika może mieć maksymalnie 100 znaków")
        String dieNumber,

        @NotNull(message = "Liczba zębów/powtórzeń jest wymagana")
        @Min(value = 1, message = "Liczba zębów/powtórzeń musi być większa od zera")
        Integer repeatTeeth,

        @NotNull(message = "ID projektu jest wymagane")
        Long projectId,

        @NotBlank(message = "Status jest wymagany")
        @Size(max = 50, message = "Status może mieć maksymalnie 50 znaków")
        String status, // ACTIVE, INACTIVE, ARCHIVED, AWAY

        @Size(max = 100, message = "Lokalizacja może mieć maksymalnie 100 znaków")
        String storageLocation,

        @Size(max = 500, message = "Notatki mogą mieć maksymalnie 500 znaków")
        String notes,

        @Size(max = 50, message = "Nazwa maszyny może mieć maksymalnie 50 znaków")
        String machine // 🔥 NOWE POLE
) {}
