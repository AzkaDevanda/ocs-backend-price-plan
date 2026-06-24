package com.sts.sinorita.dto.response.priceplan;

import com.sts.sinorita.dto.response.offerver.OfferVerDetailResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetDetailOfferAndVersion {
    private Integer offerId;
    private String pricePlanName;
    private String pricePlanCode;
    private Character pricePlanType;
    private String remarks;
    //    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effDate;
    //    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expDate;
    private Character applyLevel;
    private Integer servType;
    private String servTypeName;
    private Character networkType;
    private String networkTypeName;
    private List<OfferVerDetailResponse> offerVerList;
}

