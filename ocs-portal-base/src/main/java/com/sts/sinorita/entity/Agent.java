package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AGENT")
public class Agent implements Serializable {
    @Id
    @Column(name = "AGENT_ID", nullable = false)
    private Long agentId;

    @Column(name = "RANK_ID")
    private Long rankId;

    @Column(name = "EVA_CYCLE_TYPE_ID")
    private Long evaCycleTypeId;

    @Column(name = "TMP_ID")
    private Long tmpId;

    @Column(name = "AGENT_RANGE", length = 255)
    private String agentRange;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @Column(name = "LEGAL_REP", length = 60)
    private String legalRep;

    @Column(name = "AGENT_TYPE_ID")
    private Long agentTypeId;

    @Column(name = "CONTACT_NAME", nullable = false, length = 60)
    private String contactName;

    @Column(name = "CONTACT_INFO", nullable = false, length = 120)
    private String contactInfo;

    @Column(name = "CONTRACT", length = 4000)
    private String contract;

    @Column(name = "STAFF_ID")
    private Long staffId;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PARENT_AGENT_ID")
    private Long parentAgentId;

    @Column(name = "AGENT_NAME", length = 255)
    private String agentName;

    @Column(name = "AGENT_CODE", length = 60)
    private String agentCode;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;
}
