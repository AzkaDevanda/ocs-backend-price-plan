package com.ocs.portal.dto.response.rankUp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryUpRuleResponseDto {
    private Integer upRuleId;
    private Long upId;
    private Integer priority;
    private String ruleScript;
    private String ruleComments;
    private String scriptPage;
    private Long scriptTempletId;
}
