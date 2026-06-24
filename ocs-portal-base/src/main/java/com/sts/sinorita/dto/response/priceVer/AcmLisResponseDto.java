package com.sts.sinorita.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AcmLisResponseDto {
    private Long priceVerId;

    private String accumulation;

    private Integer rum;

    private Integer resourceId;

    private String resourceName;

    private Integer reAttr;

    private String reAttrName;

    private LocalDate effDate;

    private LocalDate expDate;

    private String acmName;

    private Integer priceId;

    private Integer srcPriceId;

    private String comments;

    private Integer refValueId;

    private String shareFlag;

    private Integer ratePlanId;

    private String ratePlanType;

    private Integer offerVerId;

    private Integer mappingId;
}
