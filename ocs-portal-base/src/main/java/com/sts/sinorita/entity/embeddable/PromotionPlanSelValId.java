package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPlanSelValId implements Serializable {

  @Column(name = "PROMOTION_PLAN_ID", nullable = false)
  private Long promotionPlanId;

  @Column(name = "CONDITION_TYPE_ID", nullable = false)
  private Integer conditionTypeId;

  @Column(name = "SEQ", nullable = false)
  private Integer seq;
}
