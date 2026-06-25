package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.CdrTemplateDto;
import com.ocs.portal.projection.balanceAdjustment.SelectCdrTemplateProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CdrTemplateMapper {
  CdrTemplateDto toCdrTemplateDto (SelectCdrTemplateProjection projection);
}
