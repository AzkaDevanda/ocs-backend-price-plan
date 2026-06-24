package com.sts.sinorita.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DpDto {
    private int dpId;
    private String dpName;
    private int priority;
    private String dpType;
    private String dpTypeName;
    private int pricePlanVerId;
    private String billingPlanType;
    private String comments;
}
