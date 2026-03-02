package com.flexo.polymer.controller;

import com.flexo.polymer.dto.CreatePolymerRequest;
import com.flexo.polymer.dto.PolymerResponse;
import com.flexo.polymer.service.PolymerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/polymers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true") // Pozwala na komunikację z frontendem React
public class PolymerController {

    private final PolymerService polymerService;

    // Pobieranie listy z opcjonalnym filtrowaniem po projekcie i stronicowaniem
    @GetMapping
    public ResponseEntity<Page<PolymerResponse>> getAll(
            @RequestParam(required = false) Integer projectId,
            Pageable pageable) {
        return ResponseEntity.ok(polymerService.getAll(projectId, pageable));
    }

    // Pobieranie pojedynczego polimeru po ID
    @GetMapping("/{id}")
    public ResponseEntity<PolymerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(polymerService.getById(id));
    }

    // Tworzenie nowego polimeru
    @PostMapping
    public ResponseEntity<PolymerResponse> create(@Valid @RequestBody CreatePolymerRequest request) {
        PolymerResponse created = polymerService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Aktualizacja istniejącego polimeru
    @PutMapping("/{id}")
    public ResponseEntity<PolymerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CreatePolymerRequest request) {
        return ResponseEntity.ok(polymerService.update(id, request));
    }

    // Usuwanie polimeru
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        polymerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}