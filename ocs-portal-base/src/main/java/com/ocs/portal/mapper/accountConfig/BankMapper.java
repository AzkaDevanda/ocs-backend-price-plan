package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.response.accountconfig.BankDto;
import com.ocs.portal.projection.accountConfig.QryBankProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankDto bankDto (QryBankProjection qryBankProjection);
}
