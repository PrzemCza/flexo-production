package com.flexo.polymer.repository;

import com.flexo.polymer.model.Polymer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolymerRepository extends JpaRepository<Polymer, Long> {
    Page<Polymer> findByProjectId(Integer projectId, Pageable pageable);
}