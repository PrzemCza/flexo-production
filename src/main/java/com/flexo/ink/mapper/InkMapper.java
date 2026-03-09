package com.flexo.ink.mapper;

import com.flexo.ink.dto.CreateInkRequest;
import com.flexo.ink.dto.InkResponse;
import com.flexo.ink.model.Ink;

public class InkMapper {

    public static Ink toEntity(CreateInkRequest request) {
        Ink entity = new Ink();
        entity.setInkColorId(request.inkColorId());
        entity.setQuantityKg(request.quantityKg());
        entity.setStorageLocation(request.storageLocation());
        entity.setBatchNumber(request.batchNumber());
        entity.setReceivedDate(request.receivedDate());
        entity.setStatus(request.status());
        entity.setNotes(request.notes());
        entity.setMachine(request.machine());
        return entity;
    }

    public static InkResponse toResponse(Ink entity) {
        return new InkResponse(
            entity.getId(),
            entity.getInkColorId(),
            entity.getQuantityKg(),
            entity.getStorageLocation(),
            entity.getBatchNumber(),
            entity.getReceivedDate(),
            entity.getStatus(),
            entity.getNotes(),
            entity.getMachine()
        );
    }
}