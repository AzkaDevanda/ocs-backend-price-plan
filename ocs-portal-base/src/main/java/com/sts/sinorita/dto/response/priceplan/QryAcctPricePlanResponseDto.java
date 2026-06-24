package com.sts.sinorita.dto.response.priceplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryAcctPricePlanResponseDto {
    private Integer pricePlanId;
    private String applyLevel;
    private Integer priority;
    private String servType;
    private Character isPackage;
    private Integer warnLevel;
    private Integer pricePlanType;
    private String pricePlanTypeName;
    private String pricePlanName;
    private String pricePlanCode;
    private Long saleListPrice;
    private Long rentListPrice;
    private Character effType;
    private Character autoContinueFlag;
    private Integer cycleQuantity;
    private String timeUnit;
    private Character duplicateFlag;
    private LocalDate effDate;
    private LocalDate expDate;
    private String comments;
    private String state;
    private LocalDate stateDate;
    private LocalDate createdDate;
}
