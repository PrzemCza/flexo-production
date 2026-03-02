package com.flexo.polymer.mapper;

import com.flexo.polymer.dto.PolymerResponse;
import com.flexo.polymer.model.Polymer;
import org.springframework.stereotype.Component;

@Component
public class PolymerMapper {

    public static PolymerResponse toResponse(Polymer polymer) {
        if (polymer == null) return null;
        
        return new PolymerResponse(
            polymer.getId(),           // Long
            polymer.getProjectId(),    // Integer
            polymer.getRepeatTeeth(),
            polymer.getLengthMm(),
            polymer.getWidthMm(),
            polymer.getColorId(),
            polymer.getStatus(),
            polymer.getStorageLocation(),
            polymer.getCreatedDate(),
            polymer.getNotes(),
            polymer.getMachine()
        );
    }
}