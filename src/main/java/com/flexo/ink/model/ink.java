package com.flexo.ink.model;

import java.math.BigDecimal;
import java.time.LocalDate; // Dodano dla precyzji numeric(10,2)

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

    @Column(name = "quantity_kg", precision = 10, scale = 2)
    private BigDecimal quantityKg; // Zmieniono z Double na BigDecimal

    @Column(name = "storage_location", length = 100)
    private String storageLocation;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "notes", length = 255)
    private String notes;
}