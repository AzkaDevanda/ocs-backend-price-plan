package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PromotionSParamValId implements Serializable {
  @Column(name = "PROMOTION_ITEM_PP_ID", nullable = false, precision = 9, scale = 0)
  private Long promotionItemPpId;

  @Column(name = "SIMPLE_PARAM_ID", nullable = false, precision = 9, scale = 0)
  private Long simpleParamId;
}