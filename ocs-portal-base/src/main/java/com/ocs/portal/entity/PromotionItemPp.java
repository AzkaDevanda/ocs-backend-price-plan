package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PROMOTION_ITEM_PP", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionItemPp implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_item_pp_id_seq")
  @SequenceGenerator(name = "promotion_item_pp_id_seq", sequenceName = "PROMOTION_ITEM_PP_ID_SEQ", allocationSize = 1)
  @Column(name = "PROMOTION_ITEM_PP_ID", nullable = false, precision = 9, scale = 0)
  private Long promotionItemPpId;

  @Column(name = "PROMOTION_ITEM_ID", precision = 9, scale = 0)
  private Long promotionItemId;

  @Column(name = "PRICE_PLAN_ID", precision = 9, scale = 0)
  private Long pricePlanId;

  @Column(name = "PARAM_VER_ID", nullable = false, precision = 9, scale = 0)
  private Long paramVerId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}