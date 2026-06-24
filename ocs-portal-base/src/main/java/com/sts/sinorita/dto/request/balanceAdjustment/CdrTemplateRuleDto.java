package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CdrTemplateRuleDto {
  public Long cdrTemplateRuleId;

  public Long cdrTemplateId;

  public Long eventType;

  public Long contactChannelId;

  public Long paymentMethod;

  public Long spId;

  public Long eventFormatId;

  public Long subsEventId;
}
