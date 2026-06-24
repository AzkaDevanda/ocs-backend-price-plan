package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependProdDetailByOfferIdDto {
    private Long offerId;
    private String offerType;
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
    private String autoContinueFlag;
    private Integer cycleQuantity;
    private Character timeUnit;
    private Character duplicateFlag;
    private Integer expOff;
    private Character expTimeUnit;
    private Character agreementEffType;
    private Character salePriceGstType;
    private Character rentPriceGstType;
    private Integer servType;
    private String isPackage;
    private Integer lifecycleType;
    private String groupType;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String networkType;
}
