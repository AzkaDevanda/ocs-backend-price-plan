package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

@Data
public class PricePlanParamDto {
    public Long pricePlanParamId;

    public String paramType;

    public Long simpleParamId;

    public Long tableParamId;

    public Long spId;

    public Long offerVerId;

    public String insideFlag;
}
