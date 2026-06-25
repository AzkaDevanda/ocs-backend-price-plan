package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class EventCdrServiceDto {

  private Boolean isNeedCdr;

  private String cdrServiceEventCode;

  private Boolean isFail;

  private Boolean noConfig;

  private Long paymentMethodId;

  private Long contactChannelId;

  private Long subsEventId;

  private Long subsId;

  private String accNbr;

  private String prefix;

  private Long spId;

}
