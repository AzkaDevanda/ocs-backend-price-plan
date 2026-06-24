package com.sts.sinorita.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanResponseDto {
    private Integer offerId;
    private String pricePlanName;
    private String pricePlanType;
    private String pricePlanCode;
    private String validPeriod;
    private Character applyLevel;
}
