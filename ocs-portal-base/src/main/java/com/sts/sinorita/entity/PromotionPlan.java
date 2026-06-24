package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PROMOTION_PLAN", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PROMOTION_PLAN_ID", nullable = false)
  private Long promotionPlanId;

  @Column(name = "SUBS_EVENT_ID")
  private Long subsEventId;

  @Column(name = "ORDER_TYPE", length = 1)
  private String orderType;

  @Column(name = "PROMOTION_PLAN_NAME", length = 60, nullable = false)
  private String promotionPlanName;

  @Column(name = "STD_CODE", length = 30)
  private String stdCode;

  @Column(name = "PROMOTION_CHARGE", precision = 15, scale = 0, nullable = false)
  private BigDecimal promotionCharge;

  @Column(name = "IS_RATIO", length = 1, nullable = false)
  private String isRatio;

  @Column(name = "GRADE", length = 1, nullable = false)
  private String grade;

  @Lob
  @Column(name = "RULE_SCRIPTS", length = 4000)
  private String ruleScripts;

  @Column(name = "PRIORITY", precision = 3, scale = 0)
  private Integer priority;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "STATE", length = 1, nullable = false)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "COMMENTS", length = 255, nullable = false)
  private String comments;

  @Column(name = "SP_ID")
  private Long spId;
}
