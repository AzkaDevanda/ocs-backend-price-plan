package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.CustPriceBookSParamValueId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUST_PRICE_BOOK_S_PARAM_VALUE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustPriceBookSParamValue {
  @EmbeddedId
  private CustPriceBookSParamValueId id;

  @Column(name = "VAL_TYPE", nullable = false, length = 1)
  private String valType;

  @Column(name = "RATE_PRECISION")
  private Integer ratePrecision;

  @Column(name = "VALUE", nullable = false, length = 30)
  private String value;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "SALES_PRICE", length = 30)
  private String salesPrice;

  @Column(name = "SP_PRECISION")
  private Integer spPrecision;
}