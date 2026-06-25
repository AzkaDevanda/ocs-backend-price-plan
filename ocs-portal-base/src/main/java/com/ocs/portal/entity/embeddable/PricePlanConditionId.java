package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PricePlanConditionId implements Serializable {
  @Column(name = "PRICE_PLAN_ID", nullable = false)
  private Long pricePlanId;

  @Column(name = "CONDITION_PRICE_PLAN_ID", nullable = false)
  private Long conditionPricePlanId;
}