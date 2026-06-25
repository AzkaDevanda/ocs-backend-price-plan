package com.ocs.portal.dto.response.rateplan;

import lombok.Data;

import java.sql.Blob;
import java.sql.Clob;

@Data
public class QryRePricePlanByReIdAndOfferVerIdDto {
    private Long reId;
    private Long offerVerId;
    private Long spId;
    private String ruleScript;
    private String scriptPage;
    private Long scriptTempletId;
    private Long workflowId;
}
