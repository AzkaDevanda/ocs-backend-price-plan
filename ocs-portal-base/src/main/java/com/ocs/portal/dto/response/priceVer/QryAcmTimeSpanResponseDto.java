package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryAcmTimeSpanResponseDto {

    private Integer acmTimeSpanId;

    private Integer priceVerId;

    private String timeSpanName;

    private Character adjustMethod;

    private Integer timeSpanId;

    private String valueString;

    private Integer priority;

    private Long rum;

}
