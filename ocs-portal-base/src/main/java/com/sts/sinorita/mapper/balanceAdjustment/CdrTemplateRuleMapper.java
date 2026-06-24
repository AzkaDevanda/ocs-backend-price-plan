package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.CdrTemplateRuleExDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllCdrTemplateRuleProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CdrTemplateRuleMapper {
  CdrTemplateRuleExDto toCdrTemplateRuleExDto (SelectAllCdrTemplateRuleProjection projection);
}
