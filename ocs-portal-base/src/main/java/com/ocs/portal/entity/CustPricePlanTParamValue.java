package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import com.ocs.portal.entity.embeddable.CustPricePlanTParamValueId;

@Entity
@Table(name = "CUST_PRICE_PLAN_T_PARAM_VALUE", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustPricePlanTParamValue implements Serializable {
  @EmbeddedId
  private CustPricePlanTParamValueId id;

  @Column(name = "VAL_TYPE", nullable = false, length = 1)
  private String valType;

  @Column(name = "RATE_PRECISION", precision = 1, scale = 0)
  private Integer ratePrecision;

  @Column(name = "VALUE", nullable = false, length = 30)
  private String value;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "SALES_PRICE", length = 30)
  private String salesPrice;

  @Column(name = "SP_PRECISION", precision = 1, scale = 0)
  private Integer spPrecision;
}