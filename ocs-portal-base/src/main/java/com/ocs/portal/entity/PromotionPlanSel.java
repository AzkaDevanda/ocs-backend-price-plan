package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROMOTION_PLAN_SEL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPlanSel {

  @Id
  @Column(name = "PROMOTION_PLAN_ID", nullable = false)
  private Long promotionPlanId;

  @Column(name = "RULE_TPL_INST_ID")
  private Long ruleTplInstId;

  @Column(name = "SP_ID")
  private Long spId;
}
