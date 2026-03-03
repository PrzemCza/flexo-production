package com.flexo.productionmonitor.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flexo.diecut.repository.DieCutRepository;
import com.flexo.ink.repository.InkRepository;
import com.flexo.polymer.repository.PolymerRepository;
import com.flexo.productionmonitor.dto.ProjectOrderRequest;
import com.flexo.productionmonitor.dto.ProjectOrderResponse;
import com.flexo.productionmonitor.model.ProjectOrder;
import com.flexo.productionmonitor.repository.ProjectOrderRepository;
import com.flexo.rawmaterial.repository.RawMaterialRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectOrderService {

    private final ProjectOrderRepository repository;
    private final RawMaterialRepository rawMaterialRepository;
    private final DieCutRepository dieCutRepository;
    private final PolymerRepository polymerRepository;
    private final InkRepository inkRepository;
    

    @Transactional
    public void createOrder(ProjectOrderRequest request) {
        ProjectOrder order = new ProjectOrder();
        order.setOrderNumber(request.getOrderNumber());
        order.setJobName(request.getJobName());
        order.setTargetMachine(request.getTargetMachine());
        order.setDeadline(request.getDeadline());
        order.setStatus("OPEN");

        // Pobieranie i przypisywanie komponentów
        if (request.getRawMaterialId() != null) {
            order.setRawMaterial(rawMaterialRepository.findById(request.getRawMaterialId()).orElse(null));
        }
        if (request.getDieCutId() != null) {
            order.setDieCut(dieCutRepository.findById(request.getDieCutId()).orElse(null));
        }
        if (request.getPolymerId() != null) {
            order.setPolymer(polymerRepository.findById(request.getPolymerId()).orElse(null));
        }
        
        // Farby (ManyToMany)
        if (request.getInkIds() != null && !request.getInkIds().isEmpty()) {
            order.setInks(new HashSet<>(inkRepository.findAllById(request.getInkIds())));
        }

        repository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
    repository.deleteById(id);
    }

    public ProjectOrderResponse getOrderById(Long id) {
    ProjectOrder order = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Zlecenie nie istnieje"));
    return mapToResponse(order); // używamy Twojej istniejącej metody mapowania
    }

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