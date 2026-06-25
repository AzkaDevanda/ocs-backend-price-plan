package com.ocs.portal.dto;

import com.ocs.portal.dto.request.balanceAdjustment.AcctDto;
import com.ocs.portal.dto.request.balanceAdjustment.BalanceChangeExtAttrDto;
import com.ocs.portal.dto.request.balanceAdjustment.SubsDto;
import lombok.Data;

import java.util.Map;

@Data
public class DebitInputDto {
    private AcctDto acctDto;

    private Long acctId;

    private Long acctBookId;

    private String partyType;

    private String partyCode;

    private String groupType;

    private Long overdueAmount;

    private static final long serialVersionUID = 8943693821856574852L;

    private Long subsId;

    private Long batchJobId;

    private String comments;

    private Long dealLevel;

    private SubsDto[] subsDtoList;

    private Long billingCycleTypeId;

    private Long debitDealLevel;

    private Long[] debitRuleGroupIdList;

    private String dunningActionCode;

    private Long parameter;

    private Long dunningAction;

    private Long inputParameter;

    private Long spId;

    private BalanceChangeExtAttrDto extAttr;

    private Map<String, Object> extMap;
}
