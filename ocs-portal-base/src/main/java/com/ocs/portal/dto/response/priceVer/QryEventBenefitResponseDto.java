package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QryEventBenefitResponseDto {
    private Integer priceId;
    private String priceName;
    private Integer priceVerId;
    private Integer subBalTypeId;
    private Integer priority;
    private String scriptPage;
    private String value;
    private Character configType;
    private Integer reAttr;
    private String reAttrName;
    private Integer rum;
    private Integer calcPrecision;
    private String ruleScript;
    private String ruleComments;
    private Integer scriptTempletId;
    private Integer repeatCnt;
    private Integer periodId;
    private Integer acctResId;
    private Character isCurrency;
    private String acctResName;
    private String offsetOfEffectiveDateUnit;
    private String durationOfAvailabilityUnit;
    private String relEffUnitName;
    private String relExpUnitName;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Character shareFlag;
    private Integer ratePlanId;
    private Integer ratePlanType;
    private Integer offerVerId;
    private Integer mappingId;
}
