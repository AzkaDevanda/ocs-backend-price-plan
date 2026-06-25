package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.GmGoodsQuantityHisId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "GM_GOODS_QUANTITY_HIS", schema = "STS")
public class GmGoodsQuantityHis implements Serializable {

    @EmbeddedId
    private GmGoodsQuantityHisId id;

    @Column(name = "MODEL_ID", nullable = false)
    private Integer modelId;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "NEW_QUANTITY")
    private Long newQuantity;

    @Column(name = "CHANGE_TYPE", nullable = false, length = 1)
    private String changeType;

    @Column(name = "COMMENTS", length = 1000)
    private String comments;

    @Column(name = "ORG_ID", nullable = false)
    private Integer orgId;

    @Column(name = "LOCK_QUANTITY")
    private Long lockQuantity;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "REC_EFF_DATE")
    private LocalDateTime recEffDate;

    @Column(name = "REC_EXP_DATE")
    private LocalDateTime recExpDate;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Integer spId;
}
