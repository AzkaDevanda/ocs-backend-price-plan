package com.ocs.portal.dto.response.balanceAdjustment;

import lombok.Data;

@Data
public class AdviceTypeCronDto {

  private Long adviceType;

  private String cronExpression;

  private int priority;

  private String sendFlag;
}
