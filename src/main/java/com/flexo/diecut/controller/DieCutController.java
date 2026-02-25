package com.flexo.diecut.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.dto.DieCutResponse;
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
    public ResponseEntity<DieCutResponse> create(@RequestBody @Valid CreateDieCutRequest request) {
        return ResponseEntity.ok(service.createDieCut(request));
    }

    @GetMapping
    public ResponseEntity<Page<DieCutResponse>> getFiltered(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String dieNumber,
            @RequestParam(required = false) String machine,
            @RequestParam(required = false) LocalDate createdDateFrom,
            @RequestParam(required = false) LocalDate createdDateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        List<String> statuses = (status == null || status.isBlank())
                ? null
                : Arrays.asList(status.split(","));

        return ResponseEntity.ok(
                service.getFiltered(
                        statuses,
                        projectId,
                        dieNumber,
                        machine,
                        createdDateFrom,
                        createdDateTo,
                        page,
                        size,
                        sort
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DieCutResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DieCutResponse> update(
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
