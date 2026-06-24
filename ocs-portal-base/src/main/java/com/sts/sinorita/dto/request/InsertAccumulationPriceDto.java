package com.sts.sinorita.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InsertAccumulationPriceDto {

    private Integer offerVerId;
    private Integer ratePlanId;
    private Integer mappingId;
    private Integer priceVerId;
    private LocalDate effDate;
    private LocalDate expDate;
    private Integer resourceId;
    private Integer reAttrId;
    private Long calculateUnit;
    // value string
    private String accumulation;
    private String remarks;

    private Integer templateId;

    // list time span accumulation
    List<InsertTimeSpanAccumulationDto> timeSpanAccumulation;
    // list reference accumulation
    List<InsertReferenceAccumulationDto> referenceAccumulation;
    // list acm expression price
    InsertAccumulationExpressionPriceDto expressionPrice;

}
