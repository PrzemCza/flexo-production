package com.flexo.rawmaterial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.flexo.rawmaterial.model.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long>, JpaSpecificationExecutor<RawMaterial> {
}
