package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.CdrTemplateDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectCdrTemplateProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CdrTemplateMapper {
  CdrTemplateDto toCdrTemplateDto (SelectCdrTemplateProjection projection);
}
