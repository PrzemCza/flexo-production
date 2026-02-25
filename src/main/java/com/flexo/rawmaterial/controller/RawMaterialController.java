package com.flexo.rawmaterial.controller;

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

import com.flexo.rawmaterial.dto.CreateRawMaterialRequest;
import com.flexo.rawmaterial.dto.RawMaterialResponse;
import com.flexo.rawmaterial.service.RawMaterialService;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialService service;

    public RawMaterialController(RawMaterialService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<RawMaterialResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<RawMaterialResponse> create(@RequestBody CreateRawMaterialRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> update(
            @PathVariable Long id,
            @RequestBody CreateRawMaterialRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
