package com.flexo.polymer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "polymer")
@Data
@NoArgsConstructor  // <--- To jest kluczowe dla Hibernate
@AllArgsConstructor // <--- Dobra praktyka przy @Data
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



    

    // Gettery i Settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }
    
    // ... dodaj settery dla wszystkich pól, szczególnie:
    public void setMachine(String machine) { this.machine = machine; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public void setStatus(String status) { this.status = status; }
    public void setRepeatTeeth(Integer repeatTeeth) { this.repeatTeeth = repeatTeeth; }
    public void setLengthMm(Integer lengthMm) { this.lengthMm = lengthMm; }
    public void setWidthMm(Integer widthMm) { this.widthMm = widthMm; }
    public void setColorId(Integer colorId) { this.colorId = colorId; }
    public void setNotes(String notes) { this.notes = notes; }
}