package com.flexo.diecut.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.service.DieCutService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/die-cuts")
public class DieCutController {

    private final DieCutService service;

    public DieCutController(DieCutService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DieCut> create(@RequestBody @Valid CreateDieCutRequest request) {
        return ResponseEntity.ok(service.createDieCut(request));
    }

    @GetMapping
    public ResponseEntity<List<DieCut>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DieCut> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DieCut> update(
            @PathVariable Long id,
            @RequestBody @Valid CreateDieCutRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
