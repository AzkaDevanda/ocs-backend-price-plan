package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.OriNoteDto;
import com.sts.sinorita.projection.balanceAdjustment.QryOriNoteProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OriNoteMapper {
  OriNoteDto toOriNoteDto (QryOriNoteProjection projection);
}
