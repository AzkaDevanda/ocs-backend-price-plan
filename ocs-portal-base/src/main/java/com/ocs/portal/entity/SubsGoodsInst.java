package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_GOODS_INST", schema = "STS")
public class SubsGoodsInst implements Serializable {

    @Id
    @Column(name = "SUBS_GOODS_INST_ID")
    private Long subsGoodsInstId;

    @Column(name = "PROD_ID", nullable = false)
    private Long prodId;

    @Column(name = "GOODS_PROD_SPEC_ID")
    private Long goodsProdSpecId;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "MODEL_UNIT_ID")
    private Long modelUnitId;

    @Column(name = "MODEL_ID")
    private Long modelId;

    @Column(name = "GOODS_SN", length = 60)
    private String goodsSn;

    @Column(name = "QTY")
    private Long qty;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "PORT_FLAG", length = 1)
    private String portFlag;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "SP_ID")
    private Long spId;
}
