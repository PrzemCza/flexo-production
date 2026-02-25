package com.flexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexo.model.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
}
