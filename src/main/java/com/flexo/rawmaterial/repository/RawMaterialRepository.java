package com.flexo.rawmaterial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexo.rawmaterial.model.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
}
