package com.flexo.rawmaterial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "raw_material")
@Getter
@Setter
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "width_mm")
    private Integer widthMm;

    @Column(name = "length_m")
    private Integer lengthM;

    @Column(name = "batch_number")
    private String batchNumber;

    private String supplier;

    @Column(name = "received_date")
    private java.sql.Date receivedDate;

    private String status;

    @Column(name = "warehouse_location")
    private String warehouseLocation;
}
