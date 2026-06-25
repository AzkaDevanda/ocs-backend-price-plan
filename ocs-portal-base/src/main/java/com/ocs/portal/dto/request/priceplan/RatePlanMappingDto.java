package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

@Data
public class RatePlanMappingDto {
    public Long reId;

    public Long offerVerId;

    public Long ratePlanId;

    public Long priority;

    public Long spId;
}
