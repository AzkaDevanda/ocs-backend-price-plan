package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.response.accountconfig.QryAcctResListResponseDto;
import com.ocs.portal.projection.accountConfig.QryAcctResListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcctResListMapper {
    QryAcctResListResponseDto toDto (QryAcctResListProjection projection);
}
