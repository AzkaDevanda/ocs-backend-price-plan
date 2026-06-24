package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.response.accountconfig.QryBalanceTypeWithMVNOResponseDto;
import com.sts.sinorita.projection.accountConfig.QryBalanceTypeWithMVNOProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryBalanceTypeWithMVNOMapper {
    QryBalanceTypeWithMVNOResponseDto toDto(QryBalanceTypeWithMVNOProjection projection);
}
