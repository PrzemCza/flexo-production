package com.flexo.ink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flexo.ink.dto.CreateInkRequest;
import com.flexo.ink.dto.InkResponse;
import com.flexo.ink.mapper.InkMapper;
import com.flexo.ink.model.ink;
import com.flexo.ink.repository.InkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InkService {

    private final InkRepository repository;

    public InkResponse create(CreateInkRequest request) {
        ink entity = InkMapper.toEntity(request);
        return InkMapper.toResponse(repository.save(entity));
    }

    public List<InkResponse> getAll() {
        return repository.findAll().stream()
                .map(InkMapper::toResponse)
                .toList();
    }

    public InkResponse getById(Long id) {
        return repository.findById(id)
                .map(InkMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Ink not found with id: " + id));
    }

    public InkResponse update(Long id, CreateInkRequest request) {
        ink existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ink not found with id: " + id));

        existing.setInkColorId(request.inkColorId());
        existing.setQuantityKg(request.quantityKg());
        existing.setStorageLocation(request.storageLocation());
        existing.setBatchNumber(request.batchNumber());
        existing.setReceivedDate(request.receivedDate());
        existing.setStatus(request.status());
        existing.setNotes(request.notes());

        return InkMapper.toResponse(repository.save(existing));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}