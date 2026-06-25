package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.QryDataPrivByDataPrivCodeResponseDto;
import com.ocs.portal.projection.balanceAdjustment.QryDataPrivByDataPrivCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BfmDataPrivMapper {
  QryDataPrivByDataPrivCodeResponseDto toQryDataPrivByDataPrivCodeResponseDto (QryDataPrivByDataPrivCodeProjection projection);
}
