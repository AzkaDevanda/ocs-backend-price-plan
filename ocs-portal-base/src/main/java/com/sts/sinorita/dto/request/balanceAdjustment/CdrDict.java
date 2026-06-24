package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CdrDict {
  private String accNbr;
  private Boolean isNeedCdr;
  private Boolean noConfig;
  private Boolean isFail;
  private Long paymentMethodId;
  private Long subsEventId;
  private Long contactChannelId;
  private Long subsId;
  private Long spId;
  private String prefix;
  private String cdrServiceEventCode;
  private CdrDataDict cdrDataDict;
}
