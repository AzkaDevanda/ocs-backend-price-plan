package com.sts.sinorita.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.EventAcmInstId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVENT_ACM_INST", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventAcmInst {

    @EmbeddedId
    private EventAcmInstId id;

    @Column(name = "PRICE_VER_ID", nullable = false)
    private Long priceVerId;

    @Column(name = "RESOURCE_ID", nullable = false)
    private Integer resourceId;

    @Column(name = "ACM_VALUE", nullable = false)
    private BigDecimal acmValue;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "CYCLE_BEGIN_DATE")
    private LocalDateTime cycleBeginDate;

    @Column(name = "CYCLE_END_DATE")
    private LocalDateTime cycleEndDate;

    @Column(name = "BILLING_CYCLE_ID")
    private Integer billingCycleId;

    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}