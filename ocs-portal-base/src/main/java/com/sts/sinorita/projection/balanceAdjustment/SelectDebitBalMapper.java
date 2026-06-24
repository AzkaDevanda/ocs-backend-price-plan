package com.sts.sinorita.projection.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.DebitBalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SelectDebitBalMapper {
  DebitBalDto toSelectDebitBalResponseDto (SelectDebitBalProjection projection);

}
