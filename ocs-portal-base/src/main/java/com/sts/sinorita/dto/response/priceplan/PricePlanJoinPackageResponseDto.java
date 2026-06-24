package com.sts.sinorita.dto.response.priceplan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricePlanJoinPackageResponseDto {
    private Integer pricePlanId;
    private Integer offerId;
    private String offerName;
    private String defaultFlag;
}
