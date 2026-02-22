package com.flexo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexo.model.RawMaterial;
import com.flexo.repository.RawMaterialRepository;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialRepository repository;

    public RawMaterialController(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RawMaterial> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }
}
