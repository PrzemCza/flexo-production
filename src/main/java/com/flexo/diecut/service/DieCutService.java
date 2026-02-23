package com.flexo.diecut.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.dto.DieCutResponse;
import com.flexo.diecut.mapper.DieCutMapper;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.repository.DieCutRepository;

@Service
public class DieCutService {

    private final DieCutRepository repository;

    public DieCutService(DieCutRepository repository) {
        this.repository = repository;
    }

    public DieCutResponse createDieCut(CreateDieCutRequest request) {
        DieCut dieCut = new DieCut();
        dieCut.setDieNumber(request.dieNumber());
        dieCut.setRepeatTeeth(request.repeatTeeth());
        dieCut.setProjectId(request.projectId());
        dieCut.setStatus(request.status());
        dieCut.setStorageLocation(request.storageLocation());
        dieCut.setNotes(request.notes());
        dieCut.setCreatedDate(LocalDate.now());

        return DieCutMapper.toResponse(repository.save(dieCut));
    }

    public List<DieCutResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(DieCutMapper::toResponse)
                .toList();
    }

    public DieCutResponse getById(Long id) {
        return repository.findById(id)
                .map(DieCutMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("DieCut not found: " + id));
    }

    public DieCutResponse update(Long id, CreateDieCutRequest request) {
        DieCut dieCut = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DieCut not found: " + id));

        dieCut.setDieNumber(request.dieNumber());
        dieCut.setRepeatTeeth(request.repeatTeeth());
        dieCut.setProjectId(request.projectId());
        dieCut.setStatus(request.status());
        dieCut.setStorageLocation(request.storageLocation());
        dieCut.setNotes(request.notes());

        return DieCutMapper.toResponse(repository.save(dieCut));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
