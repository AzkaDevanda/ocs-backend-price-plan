package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.DebitBalInstallDto;
import com.ocs.portal.projection.balanceAdjustment.SelectDebitBalInstallProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitBalInstallMapper {
  DebitBalInstallDto toDebitBalInstallDto (SelectDebitBalInstallProjection projection);
}
