package com.ocs.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubsPricePlanDto {
    private Integer pricePlanId;
    private String applyLevel;
    private Integer priority;
    private String servType;
    private String isPackage;
    private String warnLevel;
    private String pricePlanType;
    private String pricePlanTypeName;
    private String pricePlanName;
    private String pricePlanCode;
    private Double saleListPrice;
    private Double rentListPrice;
    private String effType;
    private String autoContinueFlag;
    private Integer cycleQuantity;
    private Character timeUnit;
    private Character duplicateFlag;
    private LocalDate effDate;
    private LocalDate expDate;
    private String comments;
    private String state;
    private LocalDate stateDate;
    private LocalDate createdDate;
}
