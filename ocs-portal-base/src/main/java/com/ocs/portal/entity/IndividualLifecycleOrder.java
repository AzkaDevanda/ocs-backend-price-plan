package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "INDIVIDUAL_LIFECYCLE_ORDER", schema = "STS")
public class IndividualLifecycleOrder {

    @Id
    @Column(name = "INDIVIDUAL_LIFECYCLE_ORDER_ID", nullable = false)
    private Long individualLifecycleOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "PROD_STATE", nullable = false, length = 1)
    private String prodState;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "OLD_EFF_DATE")
    private LocalDateTime oldEffDate;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
