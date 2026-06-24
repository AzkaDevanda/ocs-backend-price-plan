package com.sts.sinorita.dto.response.trigger;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriggerRuleResponseDto {

    private Integer triggerId;
    private Integer seq;
    private String effDate;
    private Integer offerVerId;
    private String expDate;
    private Character state;
    private String stateDate;
    private Integer spId;
    @Lob
    private String ruleScript;
    @Lob
    private String scriptPage;
    private Integer scriptTempletId;
    private Integer workflowId;

}

