package com.ocs.portal.dto.response.offer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetOfferByOfferCatg {
    private Integer offerId;
    private String offerName;
    private String offerCode;
    private String pricePlanType;
    private String servTypeName;
    private String networkTypeName;
    private String effType;
    private LocalDate effDate;
    private LocalDate expDate;
    private Integer childOfferCatgId;
    private String offerCatgName;

}
