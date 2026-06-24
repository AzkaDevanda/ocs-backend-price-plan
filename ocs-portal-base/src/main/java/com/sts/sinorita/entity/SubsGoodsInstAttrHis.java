package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsGoodsInstAttrHisId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SUBS_GOODS_INST_ATTR_HIS")
public class SubsGoodsInstAttrHis implements Serializable {
    @EmbeddedId
    private SubsGoodsInstAttrHisId id;

    @Column(name = "VALUE", length = 60)
    private String value;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "REC_EFF_DATE", nullable = false)
    private LocalDateTime recEffDate;

    @Column(name = "REC_EXP_DATE", nullable = false)
    private LocalDateTime recExpDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "UPLOAD_TYPE", length = 1)
    private String uploadType;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
