package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanVerUpdateDto {
    private String priceName;
    private String price;
    private String payIndicator;
    private Long rum;
    private Integer acctItemTypeId;
    private String comments;
    private Integer reAttr;
//    LocalDate effDate;
//    LocalDate expDate;

    List<TimeSpanUpRequest> timeSpanUp;

    List<RankDto> rankUp;

    List<AcmUpDto> accumulationPrice;

    List<AcmCalcDto> accumulationCalculation;

    ExpressionPriceRequest expressionPrice;
}
