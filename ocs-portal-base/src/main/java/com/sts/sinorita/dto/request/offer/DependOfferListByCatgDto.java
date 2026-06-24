package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependOfferListByCatgDto {
    private Integer seq;
    private Integer offerCatgMemId;
    private Integer offerId;
    private Character offerType;
    private String offerName;
    private String comments;
    private String offerCode;
    private Long saleListPrice;
    private Long rentListPrice;
    private LocalDate effDate;
    private LocalDate expDate;
    private LocalDate createdDate;
    private Character state;
    private LocalDate stateDate;
    private String effType;
    private Integer expOff;
    private Character timeUnit;
    private Character autoContinueFlag;
    private Integer cycleQuantity;
    private Character duplicateFlag;
    private Integer spId;
    private Character expTimeUnit;
    private Character agreementEffType;
    private String salesRuleScript;
    private Character salePriceGstType;
    private Character rentPriceGstType;
    private Character prodType;
    private Character publishState;
    private LocalDate publishStateDate;
    private String isPackage;
    private Integer servType;
    private String servTypeName;
}
