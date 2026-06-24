package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsGoodsInstHisId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "SUBS_GOODS_INST_HIS")
public class SubsGoodsInstHis {
    @EmbeddedId
    private SubsGoodsInstHisId id;

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

    @Column(name = "REC_EFF_DATE", nullable = false)
    private LocalDateTime recEffDate;

    @Column(name = "REC_EXP_DATE", nullable = false)
    private LocalDateTime recExpDate;

    @Column(name = "PORT_FLAG", length = 1)
    private String portFlag;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Long partId;

    @Column(name = "PACKAGE_OFFER_ID")
    private Long packageOfferId;

    @Column(name = "PACKAGE_INST_ID")
    private Long packageInstId;

    @Column(name = "REC_CREATED_DATE")
    private LocalDateTime recCreatedDate;

    @Column(name = "IS_GIFT", length = 1)
    private String isGift;

    @Lob
    @Column(name = "EXT_ATTR")
    private String extAttr;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "COMPLETED_DATE")
    private LocalDateTime completedDate;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "UPLOAD_TYPE", length = 1)
    private String uploadType;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "AGREEMENT_EFF_DATE")
    private LocalDateTime agreementEffDate;

    @Column(name = "AGREEMENT_EXP_DATE")
    private LocalDateTime agreementExpDate;

    @Column(name = "AGREEMENT_TIME_UNIT", length = 1)
    private String agreementTimeUnit;

    @Column(name = "AGREEMENT_LIMIT")
    private Long agreementLimit;
}
