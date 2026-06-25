package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryAcmRefResponseDto {
    private Integer acmRefId;

    private Integer priceVerId;

    private Integer resourceId;

    private String resourceName;

    private Character adjustMethod;

    private Integer acmTimeSpanId;

    private Integer rate;

    private Long rum;

    private Long effValue;

    private Long expValue;

    private Integer acmTimeSpanPriority;
}
