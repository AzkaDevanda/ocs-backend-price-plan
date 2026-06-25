package com.ocs.portal.dto.request.priceplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfoDto {
    private Long priceId;
    private String priceName;
    private String payIndicator;
    private Integer priceAcctItemTypeId;
    private String value;
    private Character calcPrecision;
    private Long rum;
    private Integer reAttr;
    private String reAttrName;
    private String comments;
    private Integer priceVerId;
    private Integer calcDisAitId;
    private Integer parentPriceId;
    private Integer mappingId;
    private LocalDate effDate;
    private LocalDate expDate;
    private String acctItemTypeName;
    private Integer acctItemTypeId;
    private String acctResName;
    private Character isCurrency;
    private Long creditLimit;
    private Integer priority;
    private Character ratePrecision;
    private Integer depositTypeId;
    private String depositTypeName;
    private String param;
    private Character shareFlag;
    private Integer ratePlanId;
    private Character ratePlanType;
    private Integer offerVerId;
    private Integer acctItemTypeIdParam;
    private String valueString;
}
