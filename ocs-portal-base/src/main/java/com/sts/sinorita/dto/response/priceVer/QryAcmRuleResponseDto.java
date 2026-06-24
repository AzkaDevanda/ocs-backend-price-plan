package com.sts.sinorita.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.sql.Blob;
import java.sql.Clob;

@Data
@Builder
public class QryAcmRuleResponseDto {
    private Integer acmRuleId;

    private String ruleScript;

    private Integer priority;

    private String ruleComments;

    private Integer priceVerId;

    private Integer scriptTempletId;

    private String scriptPage;
}
