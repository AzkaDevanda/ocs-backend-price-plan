package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.PricePlanConditionId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRICEPLAN_CONDITION", schema = "STS", indexes = {
    @Index(name = "IDX_PRICEPLAN_CONDITION_ID", columnList = "CONDITION_PRICE_PLAN_ID"),
    @Index(name = "PK_PRICEPLAN_CONDITION", columnList = "PRICE_PLAN_ID, CONDITION_PRICE_PLAN_ID", unique = true)
})
public class PricePlanCondition {

  @EmbeddedId
  private PricePlanConditionId id;
}