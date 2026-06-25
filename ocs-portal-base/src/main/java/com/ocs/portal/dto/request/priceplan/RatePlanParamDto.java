package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

@Data
public class RatePlanParamDto {
    public Long ratePlanParamId;

    public Long ratePlanId;

    public String paramType;

    public Long simpleParamId;

    public Long tableParamId;

    public Long spId;
}
