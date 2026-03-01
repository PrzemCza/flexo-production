package com.flexo.ink.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ink_container")
@Getter
@Setter
public class ink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ink_color_id")
    private Integer inkColorId;

    @Column(name = "quantity_kg", columnDefinition = "numeric")
    private Double quantityKg; 

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "machine")
    private String machine;

    @Column(name = "notes")
    private String notes;
}