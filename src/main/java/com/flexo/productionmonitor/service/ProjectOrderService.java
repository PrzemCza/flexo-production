package com.flexo.productionmonitor.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flexo.diecut.repository.DieCutRepository;
import com.flexo.ink.repository.InkRepository; // upewnij się, że import jest poprawny
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

    @Transactional
    public void updateOrder(Long id, ProjectOrderRequest request) {
        ProjectOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zlecenie o ID " + id + " nie istnieje."));
        updateOrderFields(order, request);
        repository.save(order);
    }

    private void updateOrderFields(ProjectOrder order, ProjectOrderRequest request) {
        order.setOrderNumber(request.getOrderNumber());
        order.setJobName(request.getJobName());
        order.setTargetMachine(request.getTargetMachine());
        order.setDeadline(request.getDeadline());

        order.setRawMaterial(request.getRawMaterialId() != null ? 
            rawMaterialRepository.findById(request.getRawMaterialId()).orElse(null) : null);
        
        order.setDieCut(request.getDieCutId() != null ? 
            dieCutRepository.findById(request.getDieCutId()).orElse(null) : null);
        
        order.setPolymer(request.getPolymerId() != null ? 
            polymerRepository.findById(request.getPolymerId()).orElse(null) : null);
        
        if (request.getInkIds() != null) {
            order.setInks(new HashSet<>(inkRepository.findAllById(request.getInkIds())));
        }
    }

    public List<ProjectOrderResponse> getAllOrdersWithMonitor() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectOrderResponse getOrderWithMonitor(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zlecenia"));
    }

    @Transactional
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

private ProjectOrderResponse mapToResponse(ProjectOrder order) {
    String target = order.getTargetMachine();

    // 1. Surowiec (RawMaterial) - używamy getBatchNumber()
    boolean rmReady = order.getRawMaterial() != null && 
                      target.equalsIgnoreCase(order.getRawMaterial().getAssignedMachine());
    String rmName = order.getRawMaterial() != null ? 
                    "Partia: " + order.getRawMaterial().getBatchNumber() : "Nie przypisano";
    
    // 2. Wykrojnik (DieCut) - używamy getDieNumber()
    boolean dcReady = order.getDieCut() != null && 
                      target.equalsIgnoreCase(order.getDieCut().getMachine());
    String dcName = order.getDieCut() != null ? 
                    order.getDieCut().getDieNumber() : "Nie przypisano";
                          
    // 3. Polimer (Polymer) - używamy getProjectId()
    boolean polyReady = order.getPolymer() != null && 
                        target.equalsIgnoreCase(order.getPolymer().getMachine());
    String polyName = order.getPolymer() != null ? 
                      "Projekt: " + order.getPolymer().getProjectId() : "Nie przypisano";

    // 4. Farby (ink - mała litera zgodnie z Twoją encją)
    // Używamy getInkColorId() lub getBatchNumber()
    List<String> inkList = order.getInks() != null ? 
        order.getInks().stream()
             .map(i -> "Kolor ID: " + i.getInkColorId())
             .collect(Collectors.toList()) : List.of();
    
    long totalInks = inkList.size();
    long readyInksCount = order.getInks() != null ? order.getInks().stream()
            .filter(i -> target.equalsIgnoreCase(i.getMachine()))
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
            readyInksCount + "/" + totalInks,
            rmName,
            dcName,
            polyName,
            inkList
    );
}
}