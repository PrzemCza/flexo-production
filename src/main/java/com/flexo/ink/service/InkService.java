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

    private void applyStatusLogic(ink inkEntity, CreateInkRequest request) {
        String status = request.status();
        String machine = request.machine();

        switch (status) {
            case "ACTIVE" -> {
                if (machine == null || machine.isBlank()) {
                    throw new IllegalArgumentException("Dla statusu ACTIVE musisz wybrać maszynę (E5, P5, P7, P7-11).");
                }
                inkEntity.setMachine(machine);
                inkEntity.setStorageLocation("Produkcja - " + machine);
            }
            case "INACTIVE" -> {
                inkEntity.setMachine(null);
                // Jeśli nie podano lokalizacji, ustawiamy domyślną dla magazynu
                if (request.storageLocation() == null || request.storageLocation().isBlank()) {
                    inkEntity.setStorageLocation("Magazyn Główny");
                } else {
                    inkEntity.setStorageLocation(request.storageLocation());
                }
            }
            default -> throw new IllegalArgumentException("Dozwolone statusy to: ACTIVE, INACTIVE. Otrzymano: " + status);
        }
    }

    public InkResponse create(CreateInkRequest request) {
        ink entity = InkMapper.toEntity(request);
        applyStatusLogic(entity, request);
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
        existing.setBatchNumber(request.batchNumber());
        existing.setReceivedDate(request.receivedDate());
        existing.setStatus(request.status());
        existing.setNotes(request.notes());

        applyStatusLogic(existing, request);

        return InkMapper.toResponse(repository.save(existing));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}