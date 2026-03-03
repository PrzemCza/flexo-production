package com.flexo.productionmonitor.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProjectOrderRequest {
    private String orderNumber;
    private String jobName;
    private String targetMachine;
    private Long rawMaterialId;
    private Long dieCutId;
    private Long polymerId;
    private List<Long> inkIds;
    private LocalDate deadline;
}