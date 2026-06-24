package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.AddDDParamDto;
import com.sts.sinorita.projection.accountConfig.QryDDParamProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DDParamMapper {
    AddDDParamDto DDParamDto(QryDDParamProjection qryDDParamProjection);
}
