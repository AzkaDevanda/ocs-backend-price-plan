package com.ocs.portal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PricePlanExDto {
  private Long pricePlanId;

  private String applyLevel;

  private String pricePlanName;

  private String comments;

  private String state;

  private LocalDateTime stateDate;

  private Long priority;

  private String pricePlanCode;

  private Long spId;
}
