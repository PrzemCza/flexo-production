package com.flexo.diecut.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.repository.DieCutRepository;

@Service
public class DieCutService {

    private final DieCutRepository repository;

    public DieCutService(DieCutRepository repository) {
        this.repository = repository;
    }

    public DieCut createDieCut(CreateDieCutRequest request) {
        DieCut dieCut = new DieCut();
        dieCut.setDieNumber(request.dieNumber());
        dieCut.setRepeatTeeth(request.repeatTeeth());
        dieCut.setProjectId(request.projectId());
        dieCut.setStatus(request.status());
        dieCut.setStorageLocation(request.storageLocation());
        dieCut.setNotes(request.notes());
        dieCut.setCreatedDate(LocalDate.now());

        return repository.save(dieCut);
    }

    public List<DieCut> getAll() {
        return repository.findAll();
    }

    public DieCut getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DieCut not found: " + id));
    }

    public DieCut update(Long id, CreateDieCutRequest request) {
        DieCut dieCut = getById(id);

        dieCut.setDieNumber(request.dieNumber());
        dieCut.setRepeatTeeth(request.repeatTeeth());
        dieCut.setProjectId(request.projectId());
        dieCut.setStatus(request.status());
        dieCut.setStorageLocation(request.storageLocation());
        dieCut.setNotes(request.notes());

        return repository.save(dieCut);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
