package com.flexo.productionmonitor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flexo.productionmonitor.dto.ProjectOrderResponse;
import com.flexo.productionmonitor.model.ProjectOrder;
import com.flexo.productionmonitor.repository.ProjectOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectOrderService {

    private final ProjectOrderRepository repository;

    /**
     * Pobiera wszystkie zlecenia i mapuje je na DTO z monitorowaniem statusów.
     * Ta metoda naprawi błąd w Twoim kontrolerze.
     */
    public List<ProjectOrderResponse> getAllOrdersWithMonitor() {
        return repository.findAllWithDetails().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectOrderResponse getOrderWithMonitor(Long id) {
        ProjectOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zlecenie o ID " + id + " nie istnieje."));
        return mapToResponse(order);
    }

    private ProjectOrderResponse mapToResponse(ProjectOrder order) {
        String target = order.getTargetMachine();

        // 1. Surowiec: sprawdzamy assigned_machine
        boolean rmReady = order.getRawMaterial() != null && 
                          target.equalsIgnoreCase(order.getRawMaterial().getAssignedMachine());
        
        // 2. Wykrojnik: sprawdzamy machine
        boolean dcReady = order.getDieCut() != null && 
                          target.equalsIgnoreCase(order.getDieCut().getMachine());
                          
        // 3. Polimery: (Teczka projektu) sprawdzamy machine
        boolean polyReady = order.getPolymer() != null && 
                            target.equalsIgnoreCase(order.getPolymer().getMachine());

        // 4. Farby: Wszystkie przypisane muszą być na właściwej maszynie
        long totalInks = order.getInks() != null ? order.getInks().size() : 0;
        long readyInksCount = totalInks > 0 ? order.getInks().stream()
                .filter(ink -> target.equalsIgnoreCase(ink.getMachine()))
                .count() : 0;
        
        boolean inksReady = totalInks > 0 && readyInksCount == totalInks;

        return new ProjectOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getJobName(),
                target,
                order.getStatus(),
                order.getDeadline(),
                rmReady,
                dcReady,
                polyReady,
                inksReady,
                readyInksCount + "/" + totalInks
        );
    }
}