package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_BASE_ORDER", schema = "STS")
public class SubsBaseOrder implements Serializable {

    @Id
    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "INDEP_PROD_SPEC_ID")
    private Long indepProdSpecId;

    @Column(name = "DEF_LANG_ID")
    private Long defLangId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "AGENT_ID")
    private Long agentId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "PPS_PWD", length = 120)
    private String ppsPwd;

    @Column(name = "PPS_CREDIT_LIMIT")
    private Long ppsCreditLimit;

    @Column(name = "SUBS_PLAN_ID")
    private Long subsPlanId;


    @Column(name = "AGM_EFF_DATE")
    private LocalDateTime agmEffDate;

    @Column(name = "AGE_EXP_DATE")
    private LocalDateTime ageExpDate;

    @Column(name = "OLD_CUST_ID")
    private Long oldCustId;

    @Column(name = "OLD_USER_ID")
    private Long oldUserId;

    @Column(name = "OLD_ACCT_ID")
    private Long oldAcctId;

    @Column(name = "OLD_AREA_ID")
    private Long oldAreaId;

    @Column(name = "OLD_AGENT_ID")
    private Long oldAgentId;

    @Column(name = "OLD_ORG_ID")
    private Long oldOrgId;

    @Column(name = "OLD_DEF_LANG_ID")
    private Long oldDefLangId;

    @Column(name = "OLD_PPS_PWD", length = 120)
    private String oldPpsPwd;

    @Column(name = "OLD_AGM_EFF_DATE")
    private LocalDateTime oldAgmEffDate;

    @Column(name = "OLD_AGE_EXPIRY_DATE")
    private LocalDateTime oldAgeExpiryDate;

    @Column(name = "OLD_SUBS_PLAN_ID")
    private Long oldSubsPlanId;

    @Column(name = "OLD_PROD_SPEC_ID")
    private Long oldProdSpecId;


    @Column(name = "OLD_EFF_DATE")
    private LocalDateTime oldEffDate;

     @Column(name = "OLD_EXP_DATE")
    private LocalDateTime oldExpDate;

    @Column(name = "OLD_PPS_CREDIT_LIMIT")
    private Long oldPpsCreditLimit;

     @Column(name = "COMPLETED_DATE")
    private LocalDateTime completedDate;

    @Column(name = "ABS_EFF_DATE")
    private LocalDateTime absEffDate;

    @Column(name = "ABS_EXP_DATE")
    private LocalDateTime absExpDate;

    @Column(name = "REL_EFF_OFFSET")
    private Long relEffOffset;

    @Column(name = "REL_EFF_UNIT", length = 1)
    private String relEffUnit;

    @Column(name = "REL_EXP_OFFSET")
    private Long relExpOffset;

    @Column(name = "REL_EXP_UNIT", length = 1)
    private String relExpUnit;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "OLD_ROUTING_ID")
    private Long oldRoutingId;

    @Column(name = "OPERATION_TYPE", length = 1, nullable = false)
    private String operationType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "REL_AGM_UNIT", length = 1)
    private String relAgmUnit;

    @Column(name = "REL_AGM_OFFSET")
    private Long relAgmOffset;

    @Column(name = "PART_ID", nullable = false)
    private Long partId;

    @Column(name = "ACTIVE_DATE")
    private LocalDateTime activeDate;

}