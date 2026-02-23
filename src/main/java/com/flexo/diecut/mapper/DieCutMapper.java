package com.flexo.diecut.mapper;

import com.flexo.diecut.dto.DieCutResponse;
import com.flexo.diecut.model.DieCut;

public class DieCutMapper {

    public static DieCutResponse toResponse(DieCut dieCut) {
        return new DieCutResponse(
                dieCut.getId(),
                dieCut.getDieNumber(),
                dieCut.getRepeatTeeth(),
                dieCut.getProjectId(),
                dieCut.getStatus(),
                dieCut.getStorageLocation(),
                dieCut.getCreatedDate(),
                dieCut.getNotes()
        );
    }
}
