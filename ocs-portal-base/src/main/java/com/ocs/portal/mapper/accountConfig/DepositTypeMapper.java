package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.DepositTypeDto;
import com.ocs.portal.projection.accountConfig.QryDepositType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositTypeMapper {
    DepositTypeDto depositType (QryDepositType qryDepositType);
}
