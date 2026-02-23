package com.flexo.diecut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.flexo.diecut.model.DieCut;

public interface DieCutRepository extends JpaRepository<DieCut, Long>, JpaSpecificationExecutor<DieCut> {
}
