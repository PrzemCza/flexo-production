package com.flexo.polymer.service;

import com.flexo.polymer.dto.CreatePolymerRequest;
import com.flexo.polymer.dto.PolymerResponse;
import com.flexo.polymer.mapper.PolymerMapper;
import com.flexo.polymer.model.Polymer;
import com.flexo.polymer.repository.PolymerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PolymerService {

    private final PolymerRepository repository;

    /**
     * Logika statusów dla polimerów:
     * ACTIVE -> W magazynie (wymaga storageLocation)
     * INACTIVE -> Na maszynie (wymaga machine)
     */
    private void applyStatusLogic(Polymer polymer, CreatePolymerRequest request) {
        String status = request.status();
        String machine = request.machine();

        switch (status) {
            case "ACTIVE" -> {
                // Polimer w magazynie
                polymer.setMachine(null);
                if (request.storageLocation() == null || request.storageLocation().isBlank()) {
                    polymer.setStorageLocation("Magazyn Polimerów");
                } else {
                    polymer.setStorageLocation(request.storageLocation());
                }
            }
            case "INACTIVE" -> {
                // Polimer wydany na maszynę
                if (machine == null || machine.isBlank()) {
                    throw new IllegalArgumentException("Dla statusu INACTIVE (na maszynie) musisz wybrać maszynę.");
                }
                polymer.setMachine(machine);
                polymer.setStorageLocation("Produkcja - " + machine);
            }
            default -> throw new IllegalArgumentException("Nieznany status: " + status);
        }
    }

    public Page<PolymerResponse> getAll(Integer projectId, Pageable pageable) {
        if (projectId != null) {
            return repository.findByProjectId(projectId, pageable).map(PolymerMapper::toResponse);
        }
        return repository.findAll(pageable).map(PolymerMapper::toResponse);
    }

    public PolymerResponse getById(Long id) {
        return repository.findById(id)
                .map(PolymerMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono polimeru o ID: " + id));
    }

    @Transactional
    public PolymerResponse create(CreatePolymerRequest request) {
        Polymer polymer = new Polymer();
        mapFields(polymer, request);
        polymer.setCreatedDate(LocalDate.now());
        
        applyStatusLogic(polymer, request);
        
        return PolymerMapper.toResponse(repository.save(polymer));
    }

    @Transactional
    public PolymerResponse update(Long id, CreatePolymerRequest request) {
        Polymer polymer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono polimeru o ID: " + id));
        
        mapFields(polymer, request);
        applyStatusLogic(polymer, request);
        
        return PolymerMapper.toResponse(repository.save(polymer));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void mapFields(Polymer polymer, CreatePolymerRequest request) {
        polymer.setProjectId(request.projectId());
        polymer.setRepeatTeeth(request.repeatTeeth());
        polymer.setLengthMm(request.lengthMm());
        polymer.setWidthMm(request.widthMm());
        polymer.setColorId(request.colorId());
        polymer.setStatus(request.status());
        polymer.setNotes(request.notes());
    }
}