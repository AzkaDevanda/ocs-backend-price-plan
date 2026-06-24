package com.sts.sinorita.dto.response.offer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OfferApplyOrgResponseDto {
    private Integer offerId;

    private Integer orgId;

    private Integer spId;

    private Character excludeFlag;

    private Integer parentOrgId;

    private Integer areaId;

    private String orgName;

    private String conditionName;

    private String orgType;

    private Character state;

    private LocalDate stateDate;

    private String orgCode;
}
