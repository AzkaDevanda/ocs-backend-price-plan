package com.sts.sinorita.dto.response.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferGroupMemByOfferIdResponseDto {
    private Integer offerGroupMemId;

    private Integer offerGroupId;

    private Integer offerId;

    private Integer agreementPeriod;

    private Character timeUnit;

    private Character defaultFlag;

    private String offerGroupName;

    private Character groupType;

    private Character shareFlag;

    private Integer upperLimit;

    private Integer lowerLimit;

    private Integer defaultNum;
}
