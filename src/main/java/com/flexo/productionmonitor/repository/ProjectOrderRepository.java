package com.flexo.productionmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flexo.productionmonitor.model.ProjectOrder;

@Repository
public interface ProjectOrderRepository extends JpaRepository<ProjectOrder, Long> {
    
    @Query("SELECT DISTINCT p FROM ProjectOrder p " +
           "LEFT JOIN FETCH p.rawMaterial " +
           "LEFT JOIN FETCH p.dieCut " +
           "LEFT JOIN FETCH p.polymer " +
           "LEFT JOIN FETCH p.inks")
    List<ProjectOrder> findAllWithDetails();
}