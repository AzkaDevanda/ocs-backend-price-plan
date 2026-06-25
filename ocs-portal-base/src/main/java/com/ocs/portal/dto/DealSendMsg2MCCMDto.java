package com.ocs.portal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DealSendMsg2MCCMDto {

  private Long acctId;

  private Long eventInstId;

  private Long subsId;

  private Long balId;

  private String bonusAmount;

  private String thresholdType;

  private String notifyType;

  private LocalDateTime notifyTime;

  private Long acctResId;

  private Long threshold;

  private String eventCode;

  private Long offerId;

  private Character isCurrency;

}
