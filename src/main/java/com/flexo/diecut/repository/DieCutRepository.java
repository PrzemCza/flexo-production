package com.flexo.diecut.repository;

import com.flexo.diecut.model.DieCut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DieCutRepository extends JpaRepository<DieCut, Long> {
}
