package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.response.accountconfig.QryBalanceTypeWithMVNOResponseDto;
import com.ocs.portal.projection.accountConfig.QryBalanceTypeWithMVNOProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryBalanceTypeWithMVNOMapper {
    QryBalanceTypeWithMVNOResponseDto toDto(QryBalanceTypeWithMVNOProjection projection);
}
