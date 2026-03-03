package com.flexo.productionmonitor.model;

import java.time.LocalDate;
import java.time.LocalDateTime; // zakładając Twoją nazwę klasy
import java.util.Set;

import com.flexo.diecut.model.DieCut;
import com.flexo.ink.model.ink;
import com.flexo.polymer.model.Polymer;
import com.flexo.rawmaterial.model.RawMaterial;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "project_order")
@Data
public class ProjectOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "target_machine", nullable = false)
    private String targetMachine;

    @OneToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @OneToOne
    @JoinColumn(name = "die_cut_id")
    private DieCut dieCut;

    @OneToOne
    @JoinColumn(name = "polymer_id")
    private Polymer polymer;

    @ManyToMany
    @JoinTable(
        name = "project_order_inks",
        joinColumns = @JoinColumn(name = "project_order_id"),
        inverseJoinColumns = @JoinColumn(name = "ink_id")
    )
    private Set<ink> inks;

    private String status;
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDate deadline;
}