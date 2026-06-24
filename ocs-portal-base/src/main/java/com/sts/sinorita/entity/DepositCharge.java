package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.DepositChargeId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "DEPOSIT_CHARGE", schema = "STS")
@Data
public class DepositCharge {

    @EmbeddedId
    private DepositChargeId id;

    @Column(name = "DEPOSIT_TYPE_ID", nullable = false)
    private Integer depositTypeId;

    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "RESERVE_CHARGE")
    private Long reserveCharge;
}
