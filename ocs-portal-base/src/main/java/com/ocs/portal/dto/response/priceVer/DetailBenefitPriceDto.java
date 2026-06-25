package com.ocs.portal.dto.response.priceVer;

import lombok.Data;

import java.util.Date;

@Data
public class DetailBenefitPriceDto {
    private Integer priceId;
    private String priceName;
    private String remarks;
    private String benefitValue;
    private String acctBalanceType;
    private Integer calculationUnit;
    private String reAttrName;
    private Integer cycleFloorLimit;
    private Integer cycleCeilLimit;
    private Integer dailyFloorLimit;
    private Integer dailyCeilLimit;
    private Integer maximumDays;
    private Character subscriptionOnly;
    private Date absoluteEffectiveDate;
    private Date absoluteExpiryDate;
    private Integer offsetEffectiveDate;
    private Character effUnit;
    private Integer durationAvaibility;
    private Character expUnit;
    private Date relativeEffTime;
    private Date relativeExpTime;
    private Character ralativePeriodUnit;
    private Integer offsetAbsoluteExpiry;
    private String rule;
    private String ruleRemarks;
    private String scriptPage;
    private Integer scriptTempletId;

}
