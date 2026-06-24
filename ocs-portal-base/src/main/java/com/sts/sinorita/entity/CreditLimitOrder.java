package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CREDIT_LIMIT_ORDER", schema = "STS")
public class CreditLimitOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREDIT_LIMIT_ORDER_SEQ")
    @SequenceGenerator(
            name = "CREDIT_LIMIT_ORDER_SEQ",
            sequenceName = "CREDIT_LIMIT_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "CREDIT_LIMIT_ORDER_ID", nullable = false)
    private Long creditLimitOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    @Column(name = "CC_TYPE", nullable = false)
    private Integer ccType;

    @Column(name = "CREDIT_LIMIT", nullable = false)
    private Long creditLimit;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "OLD_CREDIT_LIMIT")
    private Long oldCreditLimit;

    @Column(name = "OLD_EFF_DATE")
    private LocalDateTime oldEffDate;

    @Column(name = "OLD_EXP_DATE")
    private LocalDateTime oldExpDate;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}

