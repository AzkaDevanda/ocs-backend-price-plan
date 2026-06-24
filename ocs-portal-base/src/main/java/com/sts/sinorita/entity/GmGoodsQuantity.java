package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "GM_GOODS_QUANTITY", schema = "STS")
public class GmGoodsQuantity implements Serializable {

    @Id
    @Column(name = "GOODS_QUANTITY_ID", nullable = false)
    private Long goodsQuantityId;

    @Column(name = "MODEL_ID", nullable = false)
    private Integer modelId;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @Column(name = "ORG_ID", nullable = false)
    private Integer orgId;

    @Column(name = "LOCK_QUANTITY")
    private Long lockQuantity;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "COST_PRICE", precision = 15, scale = 0)
    private BigDecimal costPrice;

    @Column(name = "COMMENTS", length = 255)
    private String comments;
}
