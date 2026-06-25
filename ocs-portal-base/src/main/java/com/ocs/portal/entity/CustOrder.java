package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUST_ORDER", schema = "STS")
public class CustOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_ORDER_ID_GEN")
//    @SequenceGenerator(name = "CUST_ORDER_ID_GEN", sequenceName = "CUST_ORDER_ID_SEQ", allocationSize = 1)
    @Column(name = "CUST_ORDER_ID", nullable = false)
    private Long custOrderId;

    @Column(name = "CUST_ORDER_NBR", length = 60)
    private String custOrderNbr;

    @Column(name = "A_PARTY_CODE", length = 60)
    private String aPartyCode;

    @Column(name = "A_PARTY_TYPE", length = 1)
    private String aPartyType;

    @Column(name = "CERT_TYPE")
    private Integer certType;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "PROMOTION_PLAN_ID")
    private Long promotionPlanId;

    @Column(name = "ORDER_STATE", length = 1)
    private String orderState;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "ACCEPT_DATE", nullable = false)
    private LocalDateTime acceptDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "CONTACT", length = 60)
    private String contact;

    @Column(name = "IP_ADDRESS", length = 60)
    private String ipAddress;

    @Column(name = "CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @Column(name = "DISCOUNT_CHARGE")
    private Long discountCharge;

    @Column(name = "DISPATCH_ORDER_ID")
    private Long dispatchOrderId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", updatable = false, nullable = false)
    private Integer partId;
}
