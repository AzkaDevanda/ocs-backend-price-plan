package com.sts.sinorita.dto.response.discount;

import lombok.Data;

@Data
public class QryDpByPkDto {

    private Integer dpId;
    private Integer pricePlanVerId;
    private String dpType;
    private String dpName;
    private String comments;
    private Integer priority;
    private String dpTypeName;
    private Integer dpRuleAcctItemTypeId;
    private String dpRuleAcctItemTypeName;
    private String ruleScript;
    private String scriptPage;
    private String ruleComments;
    private Integer scriptTempletId;
    private String calcType;
    private String value;
    private Integer resultAcctItemTypeId;
    private String calcTypeName;
    private String resultAcctItemTypeName;
    private String isCurrency;
    private Character billingPlanType;
}
