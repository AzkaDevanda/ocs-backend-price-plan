package com.ocs.portal.mapper.acct;


import com.ocs.portal.dto.AcctItemTypeDetailDto;
import com.ocs.portal.dto.request.AcctItemTypeDto;
import com.ocs.portal.dto.request.AcctItemTypeQueryListDto;
import com.ocs.portal.dto.request.accountConfig.AcctItemTypeUpdateDto;
import com.ocs.portal.dto.response.QryBalanceTypeResponseDto;
import com.ocs.portal.projection.QryBalanceTypeProjection;
import com.ocs.portal.projection.acct.AcctItemTypeAscProjection;
import com.ocs.portal.projection.acct.AcctItemTypeDetailProjection;
import com.ocs.portal.projection.acct.AcctItemTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctItemTypeMapper {
    AcctItemTypeQueryListDto dto(AcctItemTypeProjection acctItemTypeProjection);
    AcctItemTypeDetailDto acctItemTypeDetailDto(AcctItemTypeDetailProjection acctItemTypeDetailProjection);
    AcctItemTypeDto acctItemTypeDto(AcctItemTypeAscProjection acctItemTypeAscProjection);
    AcctItemTypeUpdateDto acctItemTypeUpdateDto(AcctItemTypeDetailProjection acctItemTypeDetailProjection2);
    QryBalanceTypeResponseDto toQryBalanceTypeResponseDto(QryBalanceTypeProjection qryBalanceTypeProjection);
    AcctItemTypeDto toAcctItemType (AcctItemTypeProjection acctItemTypeProjection);
}
