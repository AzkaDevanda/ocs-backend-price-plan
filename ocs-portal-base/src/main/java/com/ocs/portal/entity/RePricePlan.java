package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "RE_PRICE_PLAN")
public class RePricePlan implements Serializable {
    @EmbeddedId
    private RePricePlanId id;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;

    @Lob
    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "WORKFLOW_ID")
    private Integer workflowId;
}