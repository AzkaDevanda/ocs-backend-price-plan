package com.sts.sinorita.svc.role.mapper;

import org.mapstruct.Mapper;

import com.sts.sinorita.svc.role.dto.response.QryMenuListByDirIdResponseDto;
import com.sts.sinorita.svc.role.dto.response.QryNoDirMenuListResponseDto;
import com.sts.sinorita.svc.role.projection.QryMenuListByDirIdProjection;
import com.sts.sinorita.svc.role.projection.QryNoDirMenuListProjection;

@Mapper(componentModel = "spring")
public interface BfmMenuMapper {
  QryNoDirMenuListResponseDto toQryNoDirMenuListResponseDto(QryNoDirMenuListProjection projection);
  QryMenuListByDirIdResponseDto toQryMenuListByDirIdResponseDto(QryMenuListByDirIdProjection projection);
}
