package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class GetRecurringPriceDetailResponseDto {
    private Integer priceId;
    private String priceName;
    private LocalDate effDate;
    private LocalDate expDate;
    private String valueString;
    private Integer acctItemTypeId;
    private String acctItemTypeName;
    private Long calculateUnit;
    private String comments;
    private Character ratePrecision;
    private Character calcPrecision;
    private Long creditLimit;
    private Integer priority;
    private String payIndicator;
    private Integer scriptTempletId;
    private String ruleScript;
    private String ruleComments;
    private String param;
    private String scriptPage;
    private Character configType;

    private Character rpPriceUnit;
    private String newConnection;
    private String termination;
    private String normal;
    private String inAdvance;
    private String priceByDay;
    private String priceByCycle;
    private String amount;
    private Integer roundMode;
}
