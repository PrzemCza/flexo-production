package com.flexo.ink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexo.ink.model.Ink;

@Repository
public interface InkRepository extends JpaRepository<Ink, Long> {
}
