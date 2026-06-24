package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.List;

@Data
public class CdrTemplateRuleExDto extends CdrTemplateRuleDto {
  List<CdrTemplateRuleParamDto> paramList;
}
