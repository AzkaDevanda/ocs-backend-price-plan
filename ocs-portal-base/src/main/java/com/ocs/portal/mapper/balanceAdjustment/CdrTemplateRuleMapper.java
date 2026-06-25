package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.CdrTemplateRuleExDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAllCdrTemplateRuleProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CdrTemplateRuleMapper {
  CdrTemplateRuleExDto toCdrTemplateRuleExDto (SelectAllCdrTemplateRuleProjection projection);
}
