package com.ocs.portal.dto.request;

import lombok.Data;

@Data
public class RatePlanParamExtendDto extends TariffTemplateParamDto{
    private Long ratePlanParamId;

    private Long ratePlanId;
}
