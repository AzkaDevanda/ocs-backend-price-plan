package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.response.accountconfig.QryAcctResListResponseDto;
import com.sts.sinorita.projection.accountConfig.QryAcctResListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcctResListMapper {
    QryAcctResListResponseDto toDto (QryAcctResListProjection projection);
}
