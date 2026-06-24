package com.sts.sinorita.dto.request.offer.commonoffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePlanByIdDto {
    private Integer offerId;
    private Character offerType;
    private String offerName;
    private String offerCode;
    private String comments;
    private Long saleListPrice;
    private Long rentListPrice;
    private LocalDate effDate;
    private LocalDate expDate;
    private String autoContinueFlag;
    private Integer cycleQuantity;
    private Character timeUnit;
    private Integer expOff;
    private Character expTimeUnit;
    private Character duplicateFlag;
    private String effType;
    private Integer pricePlanId;
    private Character applyLevel;
    private Integer servType;
    private Integer priority;
    private Character isPackage;
    private Character pricePlanType;
    private LocalDate createdDate;
    private Character agreementEffType;
    private Character salePriceGstType;
    private Character rentPriceGstType;
    private Character groupType;
    private Integer upperLimit;
    private Integer lowerLimit;
}
