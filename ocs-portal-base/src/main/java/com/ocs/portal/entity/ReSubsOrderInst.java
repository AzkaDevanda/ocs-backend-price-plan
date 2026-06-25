package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RE_SUBS_ORDER_INST", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReSubsOrderInst {

    @Id
    @Column(name = "EVENT_INST_ID", nullable = false)
    private Long eventInstId;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "SUB_ORDER_ID")
    private Long subOrderId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EVENT_PAYMENT_ID")
    private Long eventPaymentId;

    @Column(name = "ORDER_NUMBER", length = 60)
    private String orderNumber;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
