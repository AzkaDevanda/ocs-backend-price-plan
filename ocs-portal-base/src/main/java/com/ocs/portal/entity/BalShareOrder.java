package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BAL_SHARE_ORDER", schema = "STS")
@SequenceGenerator(
        name = "BAL_SHARE_ORDER_SEQ",
        sequenceName = "STS.BAL_SHARE_ORDER_ID_SEQ",
        allocationSize = 1
)
public class BalShareOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAL_SHARE_ORDER_SEQ")
    @Column(name = "BAL_SHARE_ORDER_ID", nullable = false)
    private Long balShareOrderId;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "BS_TEMPLATE_ID")
    private Long bsTemplateId;

    @Column(name = "CEIL_LIMIT")
    private Long ceilLimit;

    @Column(name = "DAILY_CEIL_LIMIT")
    private Long dailyCeilLimit;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "ACCT_RES_ID")
    private Long acctResId;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PAYMENT_FORCE", nullable = false, length = 1)
    private String paymentForce;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "SHARE_TYPE", length = 1)
    private String shareType;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "BAL_SHARE_ID")
    private Long balShareId;

    @Column(name = "BAL_TYPE")
    private Integer balType;
}
