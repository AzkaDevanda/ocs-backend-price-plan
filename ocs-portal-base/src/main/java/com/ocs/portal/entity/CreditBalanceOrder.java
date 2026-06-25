package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CREDIT_BALANCE_ORDER", schema = "STS")
public class CreditBalanceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREDIT_BALANCE_ORDER_SEQ")
    @SequenceGenerator(
            name = "CREDIT_BALANCE_ORDER_SEQ",
            sequenceName = "CREDIT_BALANCE_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "CREDIT_BALANCE_ORDER_ID", nullable = false)
    private Long creditBalanceOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "APPLY_LEVEL", nullable = false, length = 1)
    private String applyLevel;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "ACCT_RES_ID")
    private Long acctResId;

    @Column(name = "CREDIT_BALANCE", nullable = false)
    private Long creditBalance;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "OLD_CREDIT_BALANCE")
    private Long oldCreditBalance;

    @Column(name = "OLD_EFF_DATE")
    private LocalDateTime oldEffDate;

    @Column(name = "OLD_EXP_DATE")
    private LocalDateTime oldExpDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
