package com.ocs.portal.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRatePlanByPricePlanIdResponse {
    private Integer ratePlanId;
    private String ratePlanName;
    private Character ratePlanType;
    private Integer reId;
}
