package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustPriceBookSParamValueId implements Serializable {
  @Column(name = "PRICE_BOOK_PRICE_PLAN_ID", nullable = false)
  private Long priceBookPricePlanId;

  @Column(name = "SIMPLE_PARAM_ID", nullable = false)
  private Long simpleParamId;
}