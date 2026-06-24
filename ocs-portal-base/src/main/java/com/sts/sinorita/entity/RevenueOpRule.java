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
@Table(name = "REVENUE_OP_RULE")
public class RevenueOpRule implements Serializable {

    @Id
    @Column(name = "OP_RULE_ID", nullable = false)
    private Long opRuleId;

    @Column(name = "OP_TYPE", nullable = false, length = 30)
    private String opType;

    @Column(name = "BEGIN_CHARGE", nullable = false)
    private Long beginCharge;

    @Column(name = "END_CHARGE", nullable = false)
    private Long endCharge;

    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "STAFF_JOB_ID")
    private Long staffJobId;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "AUDIT_LEVEL")
    private Long auditLevel;
}
