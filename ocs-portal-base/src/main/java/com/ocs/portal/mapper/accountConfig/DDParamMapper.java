package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.AddDDParamDto;
import com.ocs.portal.projection.accountConfig.QryDDParamProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DDParamMapper {
    AddDDParamDto DDParamDto(QryDDParamProjection qryDDParamProjection);
}
