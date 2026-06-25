package com.ocs.portal.dto.response.rateplan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryRatePlanResponseDto {
    private Integer ratePlanId;
    private String ratePlanName;
    private Integer priority;
    private String ratePlanType;
    private String ratePlanCode;
    private String mappingExit;
    private String ratePlanMapping;
    private Integer offerVerId;
    private Integer reId;
    private String templateFlag;
    private String remarks;
}
