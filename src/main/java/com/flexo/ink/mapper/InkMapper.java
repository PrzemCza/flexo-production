package com.flexo.ink.mapper;

import com.flexo.ink.dto.CreateInkRequest;
import com.flexo.ink.dto.InkResponse;
import com.flexo.ink.model.ink;

public class InkMapper {

    public static ink toEntity(CreateInkRequest request) {
        ink ink = new ink();
        ink.setInkColorId(request.inkColorId());
        ink.setQuantityKg(request.quantityKg());
        ink.setStorageLocation(request.storageLocation());
        ink.setBatchNumber(request.batchNumber());
        ink.setReceivedDate(request.receivedDate());
        ink.setStatus(request.status());
        ink.setNotes(request.notes());
        // Mapowanie maszyny (E5, P5, P7, P7-11)
        ink.setMachine(request.machine());
        return ink;
    }

    public static InkResponse toResponse(ink ink) {
        return new InkResponse(
            ink.getId(),
            ink.getInkColorId(),
            ink.getQuantityKg(),
            ink.getStorageLocation(),
            ink.getBatchNumber(),
            ink.getReceivedDate(),
            ink.getStatus(),
            ink.getNotes(),
            ink.getMachine()
        );
    }
}