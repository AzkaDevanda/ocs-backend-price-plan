package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CdrTemplateRuleParamDto {
  public Long cdrTemplateRuleId;

  public Long seq;

  public String paramCode;

  public String paramPath;

  public String paramRule;

  public String comments;

  public Long spId;
}
