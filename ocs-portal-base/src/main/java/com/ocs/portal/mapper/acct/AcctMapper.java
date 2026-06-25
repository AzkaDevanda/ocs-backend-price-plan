package com.ocs.portal.mapper.acct;

import com.ocs.portal.dto.AcctIdentifyDto;
import com.ocs.portal.dto.request.balanceAdjustment.AcctDto;
import com.ocs.portal.dto.response.acct.QryAcctInfoResponseDto;
import com.ocs.portal.projection.acct.AcctIdentifyProjection;
import com.ocs.portal.projection.acct.QryAcctInfoProjection;
import com.ocs.portal.projection.acct.SelectAcctDtoByAcctIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctMapper {
    QryAcctInfoResponseDto toQryAcctInfoResponseDto(QryAcctInfoProjection projection);

    // BalanceChangeTriggerDict tobaBalanceChangeTriggerDict(AcctDto acctDto);

    AcctDto toAcctDtoFromSelectAcctDtoByAcctId(SelectAcctDtoByAcctIdProjection projection);

    AcctIdentifyDto toAcctIdentifyDto(AcctIdentifyProjection projection);

}
