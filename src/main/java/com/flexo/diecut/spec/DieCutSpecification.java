package com.flexo.diecut.spec;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.flexo.diecut.model.DieCut;

public class DieCutSpecification {

    public static Specification<DieCut> hasStatuses(List<String> statuses) {
        return (root, query, cb) ->
                (statuses == null || statuses.isEmpty())
                        ? null
                        : root.get("status").in(statuses);
    }

    public static Specification<DieCut> hasProjectId(Long projectId) {
        return (root, query, cb) ->
                projectId == null ? null : cb.equal(root.get("projectId"), projectId);
    }

    public static Specification<DieCut> hasDieNumberLike(String dieNumber) {
        return (root, query, cb) ->
                dieNumber == null ? null :
                        cb.like(cb.lower(root.get("dieNumber")), "%" + dieNumber.toLowerCase() + "%");
    }

    public static Specification<DieCut> createdDateFrom(LocalDate from) {
        return (root, query, cb) ->
                from == null ? null : cb.greaterThanOrEqualTo(root.get("createdDate"), from);
    }

    public static Specification<DieCut> createdDateTo(LocalDate to) {
        return (root, query, cb) ->
                to == null ? null : cb.lessThanOrEqualTo(root.get("createdDate"), to);
    }

    public static Specification<DieCut> hasMachine(String machine) {
    return (root, query, cb) ->
            machine == null || machine.isBlank()
                    ? null
                    : cb.equal(root.get("machine"), machine);
    }

}
