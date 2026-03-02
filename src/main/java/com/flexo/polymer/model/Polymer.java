package com.flexo.polymer.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "polymer")
@Data
public class Polymer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "repeat_teeth")
    private Integer repeatTeeth;

    @Column(name = "length_mm")
    private Integer lengthMm;

    @Column(name = "width_mm")
    private Integer widthMm;

    @Column(name = "color_id")
    private Integer colorId; // Zwykły Integer, bez Foreign Key

    private String status; // ACTIVE (Magazyn), INACTIVE (Maszyna)
    
    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "created_date")
    private LocalDate createdDate;

    private String notes;
    private String machine;
}