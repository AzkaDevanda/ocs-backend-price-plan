package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CdrTemplateDto {
  public Long cdrTemplateId;

  public String cdrTemplateName;

  public String cdrContent;

  public String comments;
}
