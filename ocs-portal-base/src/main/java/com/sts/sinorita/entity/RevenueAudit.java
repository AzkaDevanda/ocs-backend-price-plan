package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "REVENUE_AUDIT")
public class RevenueAudit {
    @Id
    @Column(name = "AUDIT_ID", nullable = false)
    private Long auditId;

    @Column(name = "OP_TYPE", nullable = false, length = 30)
    private String opType;

    @Column(name = "STAFF_JOB_ID", nullable = false)
    private Long staffJobId;

    @Column(name = "CONTACT_CHANNEL_ID", nullable = false)
    private Long contactChannelId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "AUDIT_STAFF_JOB_ID", nullable = false)
    private Long auditStaffJobId;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "PARTNER_SN", length = 120)
    private String partnerSn;

    @Column(name = "REVENUE_ID")
    private Long revenueId;

    @Column(name = "PRIORITY")
    private Long priority;

    @Column(name = "SCHEDULED_TIME")
    private LocalDateTime scheduledTime;

    @Column(name = "AUDIT_LEVEL")
    private Long auditLevel;

    @Column(name = "ADJUST_REASON_ID")
    private Long adjustReasonId;

    @Column(name = "APPEND_FILE", length = 255)
    private String appendFile;

    @Column(name = "REF_ID")
    private Long refId;
}
