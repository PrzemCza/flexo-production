package com.flexo.diecut.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.flexo.diecut.dto.CreateDieCutRequest;
import com.flexo.diecut.dto.DieCutResponse;
import com.flexo.diecut.mapper.DieCutMapper;
import com.flexo.diecut.model.DieCut;
import com.flexo.diecut.repository.DieCutRepository;
import com.flexo.diecut.spec.DieCutSpecification;

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

    public Page<DieCutResponse> getFiltered(
            List<String> statuses,
            Long projectId,
            String dieNumber,
            LocalDate createdDateFrom,
            LocalDate createdDateTo,
            int page,
            int size,
            String sort
    ) {
        Sort sortObj = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        Specification<DieCut> spec = Specification
                .where(DieCutSpecification.hasStatuses(statuses))
                .and(DieCutSpecification.hasProjectId(projectId))
                .and(DieCutSpecification.hasDieNumberLike(dieNumber))
                .and(DieCutSpecification.createdDateFrom(createdDateFrom))
                .and(DieCutSpecification.createdDateTo(createdDateTo));

        return repository.findAll(spec, pageable)
                .map(DieCutMapper::toResponse);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by("id").ascending();
        }

        String[] orders = sort.split(";");
        Sort finalSort = Sort.unsorted();

        for (String order : orders) {
            String[] parts = order.split(",");
            String field = parts[0];
            Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Sort newSort = Sort.by(direction, field);
            finalSort = finalSort == Sort.unsorted() ? newSort : finalSort.and(newSort);
        }

        return finalSort;
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
