package com.flexo.rawmaterial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flexo.rawmaterial.dto.CreateRawMaterialRequest;
import com.flexo.rawmaterial.dto.RawMaterialResponse;
import com.flexo.rawmaterial.mapper.RawMaterialMapper;
import com.flexo.rawmaterial.model.RawMaterial;
import com.flexo.rawmaterial.repository.RawMaterialRepository;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public RawMaterialResponse create(CreateRawMaterialRequest request) {
        RawMaterial entity = RawMaterialMapper.toEntity(request);
        RawMaterial saved = repository.save(entity);
        return RawMaterialMapper.toResponse(saved);
    }

    // GET ALL
    public List<RawMaterialResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(RawMaterialMapper::toResponse)
                .toList();
    }

    // GET BY ID
    public RawMaterialResponse getById(Long id) {
        return repository.findById(id)
                .map(RawMaterialMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found: " + id));
    }

    // UPDATE
    public RawMaterialResponse update(Long id, CreateRawMaterialRequest request) {
        RawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found: " + id));

        existing.setWidthMm(request.widthMm());
        existing.setLengthM(request.lengthM());
        existing.setBatchNumber(request.batchNumber());
        existing.setSupplier(request.supplier());
        existing.setReceivedDate(request.receivedDate());
        existing.setStatus(request.status());
        existing.setWarehouseLocation(request.warehouseLocation());

        RawMaterial saved = repository.save(existing);
        return RawMaterialMapper.toResponse(saved);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
