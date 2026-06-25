package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "RES_ORDER", schema = "STS")
@SequenceGenerator(
        name = "res_order_seq_gen",
        sequenceName = "STS.RES_ORDER_ID_SEQ",
        allocationSize = 1
)
public class ResOrder implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "res_order_seq_gen")
    @Column(name = "RES_ORDER_ID", nullable = false)
    private Long resOrderId;

    @Column(name = "RES_TYPE", nullable = false, length = 1)
    private String resType;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "RES_INST_ID")
    private Long resInstId;

    @Column(name = "OLD_RES_INST_ID")
    private Long oldResInstId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

}

