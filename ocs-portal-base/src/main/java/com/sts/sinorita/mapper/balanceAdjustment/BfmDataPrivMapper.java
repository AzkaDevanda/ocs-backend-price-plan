package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.response.balanceAdjustment.QryDataPrivByDataPrivCodeResponseDto;
import com.sts.sinorita.projection.balanceAdjustment.QryDataPrivByDataPrivCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BfmDataPrivMapper {
  QryDataPrivByDataPrivCodeResponseDto toQryDataPrivByDataPrivCodeResponseDto (QryDataPrivByDataPrivCodeProjection projection);
}
