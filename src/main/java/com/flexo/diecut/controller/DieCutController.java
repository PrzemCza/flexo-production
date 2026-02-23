package com.flexo.diecut.controller;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.service.DieCutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/die-cuts")
public class DieCutController {

    private final DieCutService service;

    public DieCutController(DieCutService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DieCut> create(@RequestBody CreateDieCutRequest request) {
        DieCut created = service.createDieCut(request);
        return ResponseEntity.ok(created);
    }
}
