package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REQUEST_OFFER")
public class RequestOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_OFFER_ID", nullable = false)
    private Integer requestOfferId;

    @Column(name = "WORK_ORDER_ID", nullable = false)
    private Long workOrderId;

    @Column(name = "CONFIG_REQUEST_ID", nullable = false)
    private Integer configRequestId;

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "CREATE_STAFF_ID", nullable = false)
    private Integer createStaffId;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "SP_ID")
    private Integer spId;

}
