package com.flexo.diecut.service;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.repository.DieCutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
