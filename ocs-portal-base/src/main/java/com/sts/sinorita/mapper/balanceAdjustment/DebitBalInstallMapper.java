package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.DebitBalInstallDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectDebitBalInstallProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitBalInstallMapper {
  DebitBalInstallDto toDebitBalInstallDto (SelectDebitBalInstallProjection projection);
}
