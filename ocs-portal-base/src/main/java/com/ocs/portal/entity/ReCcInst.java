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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RE_CC_INST", schema = "STS")
public class ReCcInst {

    @Id
    @Column(name = "EVENT_INST_ID", nullable = false)
    private Long eventInstId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "RE_TYPE", nullable = false, length = 1)
    private Character reType;

    @Column(name = "RE_ID", nullable = false)
    private Long reId;

    @Column(name = "EVENT_TIME", nullable = false)
    private LocalDateTime eventTime;

    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "BILLING_CYCLE_ID")
    private Integer billingCycleId;

    @Column(name = "CANCEL_EVENT_INST_ID")
    private Long cancelEventInstId;

    @Column(name = "INSTALMENT_TYPE_ID")
    private Integer instalmentTypeId;

    @Column(name = "PROMOTION_PLAN_ID")
    private Integer promotionPlanId;

    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "CONTACT_CHANNEL_ID")
    private Integer contactChannelId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "PARTY_TYPE", length = 1)
    private Character partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "DISCOUNT_CHARGE")
    private BigDecimal discountCharge;

    @Column(name = "COMPLETED_DATE")
    private LocalDateTime completedDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}
