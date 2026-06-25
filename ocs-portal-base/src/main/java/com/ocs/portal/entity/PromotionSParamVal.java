package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import com.ocs.portal.entity.embeddable.PromotionSParamValId;

@Entity
@Table(name = "PROMOTION_S_PARAM_VAL", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionSParamVal implements Serializable {

  @EmbeddedId
  private PromotionSParamValId id;

  @Column(name = "PARAM_VER", precision = 3, scale = 0)
  private Integer paramVer;

  @Column(name = "GROUP_ID", precision = 9, scale = 0)
  private Long groupId;

  @Column(name = "VAL_TYPE", nullable = false, length = 1)
  private String valType;

  @Column(name = "VALUE", nullable = false, length = 30)
  private String value;

  @Column(name = "STD_RATE_PRECISION", precision = 1, scale = 0)
  private Integer stdRatePrecision;

  @Column(name = "RATE_PRECISION", precision = 1, scale = 0)
  private Integer ratePrecision;

  @Column(name = "STD_VALUE", length = 30)
  private String stdValue;

  @Column(name = "DISCOUNT_PER", length = 30)
  private String discountPer;

  @Column(name = "DISCOUNT_AMT", length = 30)
  private String discountAmt;

  @Column(name = "SALES_PRICE", length = 30)
  private String salesPrice;

  @Column(name = "SP_PRECISION", precision = 1, scale = 0)
  private Integer spPrecision;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "ACCT_ITEM_TYPE_ID", precision = 6, scale = 0)
  private Long acctItemTypeId;
}
