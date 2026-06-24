package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CustPricePlanTParamValueId implements Serializable {
  @Column(name = "PRICE_BOOK_PRICE_PLAN_ID", nullable = false, precision = 9, scale = 0)
  private Long priceBookPricePlanId;

  @Column(name = "TABLE_PARAM_ID", nullable = false, precision = 9, scale = 0)
  private Long tableParamId;

  @Column(name = "CELL_ID", nullable = false, precision = 9, scale = 0)
  private Long cellId;
}