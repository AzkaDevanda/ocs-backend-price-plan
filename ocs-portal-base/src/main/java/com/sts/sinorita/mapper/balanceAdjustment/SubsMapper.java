package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectSubsProjection;
import com.sts.sinorita.projection.subs.SelectSubsByAcctIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsMapper {
  SubsDto toSubsDto(SelectSubsProjection projection);

  SubsDto toSubsDtoFromSelectLastSubs(SelectSubsProjection projection);

  SubsDto toSubsDtoFromSelectAllSubsByAcctId(SelectSubsByAcctIdProjection projection);

}
