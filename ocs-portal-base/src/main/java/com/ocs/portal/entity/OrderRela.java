package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ORDER_RELA", schema = "STS")
public class OrderRela {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_RELA_ID_SEQ")
    @SequenceGenerator(name = "ORDER_RELA_ID_SEQ", sequenceName = "ORDER_RELA_ID_SEQ", allocationSize = 1)
    @Column(name = "ORDER_RELA_ID", nullable = false)
    private Long orderRelaId;

    @Column(name = "ORDER_RELA_TYPE", length = 1)
    private String orderRelaType;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "RELA_ORDER_ITEM_ID")
    private Long relaOrderItemId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

}
