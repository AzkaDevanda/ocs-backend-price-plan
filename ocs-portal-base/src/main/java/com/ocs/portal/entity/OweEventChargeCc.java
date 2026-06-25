package com.ocs.portal.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OWE_EVENT_CHARGE_CC", schema = "STS")
public class OweEventChargeCc {

    @Id
    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "RE_ID")
    private Integer reId;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "BILLING_CYCLE_ID")
    private Integer billingCycleId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Column(name = "CHARGE")
    private BigDecimal charge;

    @Column(name = "STATE")
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "EVENT_BEGIN_TIME")
    private LocalDateTime eventBeginTime;

    @Column(name = "EVENT_END_TIME")
    private LocalDateTime eventEndTime;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PROD_RENT_DAY")
    private Integer prodRentDay;

    @Column(name = "ATTR_LIST")
    private String attrList;

    @Column(name = "OFFER_ID")
    private Integer offerId;

    @Column(name = "RECURRING_RE_TYPE")
    private Character recurringReType;

    @Column(name = "OPERATION_TYPE")
    private Character operationType;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PRICE_PLAN_ID")
    private Integer pricePlanId;

    @Column(name = "ROUTING_ID")
    private Integer routingId;

    @Column(name = "ACCT_BOOK_ID")
    private Long acctBookId;
}
