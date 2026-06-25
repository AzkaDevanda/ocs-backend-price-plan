package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GM_GOODS_INST", schema = "STS")
public class GmGoodsInst implements Serializable {

  @Id
  @Column(name = "GOODS_ID", nullable = false)
  private Long goodsId;

  @Column(name = "GOODS_TYPE_ID", length = 1, nullable = false)
  private Character goodsTypeId;

  @Column(name = "GOODS_SN", length = 255, nullable = false)
  private String goodsSn;

  @Column(name = "MODEL_ID", nullable = false)
  private Integer modelId;

  @Column(name = "GOODS_STATE", length = 1)
  private Character goodsState;

  @Column(name = "COST_PRICE", precision = 15, scale = 0)
  private BigDecimal costPrice;

  @Column(name = "ORG_ID", nullable = false)
  private Integer orgId;

  @Column(name = "STAFF_JOB_ID")
  private Integer staffJobId;

  @Column(name = "REASON_ID")
  private Integer reasonId;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "IS_SOLD", nullable = false)
  private Integer isSold;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "PARTY_TYPE", length = 1)
  private Character partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

}
