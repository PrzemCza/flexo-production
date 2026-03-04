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
        updateOrderFields(order, request);
        order.setStatus("OPEN");
        repository.save(order);
    }

    // NOWA METODA: Aktualizacja istniejącego zlecenia
    @Transactional
    public void updateOrder(Long id, ProjectOrderRequest request) {
        ProjectOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zlecenie o ID " + id + " nie istnieje."));
        
        updateOrderFields(order, request);
        
        // Opcjonalnie: aktualizacja statusu, jeśli przesyłasz go w requeście
        // order.setStatus(request.getStatus()); 

        repository.save(order);
    }

    // Prywatna metoda pomocnicza, aby nie powtarzać kodu w Create i Update
    private void updateOrderFields(ProjectOrder order, ProjectOrderRequest request) {
        order.setOrderNumber(request.getOrderNumber());
        order.setJobName(request.getJobName());
        order.setTargetMachine(request.getTargetMachine());
        order.setDeadline(request.getDeadline());

        // Surowiec
        if (request.getRawMaterialId() != null) {
            order.setRawMaterial(rawMaterialRepository.findById(request.getRawMaterialId()).orElse(null));
        } else {
            order.setRawMaterial(null);
        }

        // Wykrojnik
        if (request.getDieCutId() != null) {
            order.setDieCut(dieCutRepository.findById(request.getDieCutId()).orElse(null));
        } else {
            order.setDieCut(null);
        }

        // Polimer
        if (request.getPolymerId() != null) {
            order.setPolymer(polymerRepository.findById(request.getPolymerId()).orElse(null));
        } else {
            order.setPolymer(null);
        }

        // Farby (ManyToMany)
        if (request.getInkIds() != null) {
            order.setInks(new HashSet<>(inkRepository.findAllById(request.getInkIds())));
        } else {
            order.getInks().clear();
        }
    }

    @Transactional
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    public ProjectOrderResponse getOrderById(Long id) {
        ProjectOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zlecenie nie istnieje"));
        return mapToResponse(order);
    }

    public List<ProjectOrderResponse> getAllOrdersWithMonitor() {
        return repository.findAll().stream() // Zakładam użycie findAll, jeśli findAllWithDetails nie jest wymagane
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

        // 1. Surowiec
        boolean rmReady = order.getRawMaterial() != null && 
                          target.equalsIgnoreCase(order.getRawMaterial().getAssignedMachine());
        
        // 2. Wykrojnik
        boolean dcReady = order.getDieCut() != null && 
                          target.equalsIgnoreCase(order.getDieCut().getMachine());
                          
        // 3. Polimery
        boolean polyReady = order.getPolymer() != null && 
                            target.equalsIgnoreCase(order.getPolymer().getMachine());

        // 4. Farby
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