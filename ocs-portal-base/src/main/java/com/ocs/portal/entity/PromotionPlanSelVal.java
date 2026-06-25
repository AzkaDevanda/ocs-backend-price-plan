package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.PromotionPlanSelValId;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representasi tabel STS.PROMOTION_PLAN_SEL_VAL
 */
@Entity
@Table(name = "PROMOTION_PLAN_SEL_VAL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPlanSelVal {

  @EmbeddedId
  private PromotionPlanSelValId id;

  @Column(name = "VALUE", length = 60, nullable = false)
  private String value;

  @Column(name = "SP_ID")
  private Long spId;
}
