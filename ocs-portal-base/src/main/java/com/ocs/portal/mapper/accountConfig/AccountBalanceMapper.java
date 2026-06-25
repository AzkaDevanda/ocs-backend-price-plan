package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.AccountBalanceTypeRequestDto;
import com.ocs.portal.dto.request.accountConfig.AcctBalanceTypeListDto;
import com.ocs.portal.entity.AcctRes;
import com.ocs.portal.projection.acct.AcctBalanceTypeListProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountBalanceMapper {
    AcctRes toEntityAcctRes(AccountBalanceTypeRequestDto dto);

    @Mapping(target = "acctResId", ignore = true)
    void modAcctRes(AccountBalanceTypeRequestDto dto, @MappingTarget AcctRes acctRes);

    AcctBalanceTypeListDto dto(AcctBalanceTypeListProjection projection);
}
