package com.flexo.diecut.spec;

import org.springframework.data.jpa.domain.Specification;

import com.flexo.diecut.model.DieCut;

public class DieCutSpecification {

    public static Specification<DieCut> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<DieCut> hasProjectId(Long projectId) {
        return (root, query, cb) ->
                projectId == null ? null : cb.equal(root.get("projectId"), projectId);
    }

    public static Specification<DieCut> hasDieNumberLike(String dieNumber) {
        return (root, query, cb) ->
                dieNumber == null ? null : cb.like(cb.lower(root.get("dieNumber")), "%" + dieNumber.toLowerCase() + "%");
    }
}
