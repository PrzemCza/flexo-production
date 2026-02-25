package com.flexo.diecut.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "die_cut")
public class DieCut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "die_number", nullable = false)
    private String dieNumber;

    @Column(name = "repeat_teeth")
    private Integer repeatTeeth;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "status")
    private String status;

    @Column(name = "machine")
    private String machine;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "notes")
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDieNumber() { return dieNumber; }
    public void setDieNumber(String dieNumber) { this.dieNumber = dieNumber; }

    public Integer getRepeatTeeth() { return repeatTeeth; }
    public void setRepeatTeeth(Integer repeatTeeth) { this.repeatTeeth = repeatTeeth; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMachine() { return machine; }
    public void setMachine(String machine) { this.machine = machine; }

    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
