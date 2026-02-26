package com.flexo.rawmaterial.spec;

import org.springframework.data.jpa.domain.Specification;

import com.flexo.rawmaterial.dto.RawMaterialFilter;
import com.flexo.rawmaterial.model.RawMaterial;

public class RawMaterialSpecification {

    public static Specification<RawMaterial> build(RawMaterialFilter filter) {
        return Specification.where(widthMin(filter.widthMin()))
                .and(widthMax(filter.widthMax()))
                .and(lengthMin(filter.lengthMin()))
                .and(lengthMax(filter.lengthMax()))
                .and(batchNumber(filter.batchNumber()))
                .and(supplier(filter.supplier()))
                .and(status(filter.status()))
                .and(receivedDateFrom(filter.receivedDateFrom()))
                .and(receivedDateTo(filter.receivedDateTo()))
                .and(warehouseLocation(filter.warehouseLocation()));
    }

    private static Specification<RawMaterial> widthMin(Integer value) {
        return (root, query, cb) ->
                value == null ? null : cb.greaterThanOrEqualTo(root.get("widthMm"), value);
    }

    private static Specification<RawMaterial> widthMax(Integer value) {
        return (root, query, cb) ->
                value == null ? null : cb.lessThanOrEqualTo(root.get("widthMm"), value);
    }

    private static Specification<RawMaterial> lengthMin(Integer value) {
        return (root, query, cb) ->
                value == null ? null : cb.greaterThanOrEqualTo(root.get("lengthM"), value);
    }

    private static Specification<RawMaterial> lengthMax(Integer value) {
        return (root, query, cb) ->
                value == null ? null : cb.lessThanOrEqualTo(root.get("lengthM"), value);
    }

    private static Specification<RawMaterial> batchNumber(String value) {
        return (root, query, cb) ->
                value == null ? null : cb.like(cb.lower(root.get("batchNumber")), "%" + value.toLowerCase() + "%");
    }

    private static Specification<RawMaterial> supplier(String value) {
        return (root, query, cb) ->
                value == null ? null : cb.like(cb.lower(root.get("supplier")), "%" + value.toLowerCase() + "%");
    }

    private static Specification<RawMaterial> status(String value) {
        return (root, query, cb) ->
                value == null ? null : cb.equal(root.get("status"), value);
    }

    private static Specification<RawMaterial> receivedDateFrom(java.sql.Date value) {
        return (root, query, cb) ->
                value == null ? null : cb.greaterThanOrEqualTo(root.get("receivedDate"), value);
    }

    private static Specification<RawMaterial> receivedDateTo(java.sql.Date value) {
        return (root, query, cb) ->
                value == null ? null : cb.lessThanOrEqualTo(root.get("receivedDate"), value);
    }

    private static Specification<RawMaterial> warehouseLocation(String value) {
        return (root, query, cb) ->
                value == null ? null : cb.like(cb.lower(root.get("warehouseLocation")), "%" + value.toLowerCase() + "%");
    }
}
