package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.GmGoodsInstHisId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "GM_GOODS_INST_HIS", schema = "STS")
public class GmGoodsInstHis implements Serializable {

    @EmbeddedId
    private GmGoodsInstHisId id;

    @Column(name = "GOODS_SN", nullable = false, length = 255)
    private String goodsSn;

    @Column(name = "MODEL_ID", nullable = false)
    private Integer modelId;

    @Column(name = "PHONE", length = 60)
    private String phone;

    @Column(name = "LINKMAN_NAME", length = 120)
    private String linkmanName;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "SALE_PRICE", precision = 9, scale = 0)
    private BigDecimal salePrice;

    @Column(name = "COST_PRICE", precision = 9, scale = 0)
    private BigDecimal costPrice;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "ORG_ID", nullable = false)
    private Integer orgId;

    @Column(name = "REASON_ID")
    private Integer reasonId;

    @Column(name = "GOODS_TYPE_ID", nullable = false, length = 1)
    private String goodsTypeId;

    @Column(name = "EXTEND_ATTR", length = 4000)
    private String extendAttr;

    @Column(name = "REC_CREATED_DATE")
    private LocalDateTime recCreatedDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
