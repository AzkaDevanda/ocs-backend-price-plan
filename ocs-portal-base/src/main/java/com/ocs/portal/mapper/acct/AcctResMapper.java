package com.ocs.portal.mapper.acct;

import com.ocs.portal.dto.request.priceplan.AcctResNameListDto;
import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import com.ocs.portal.entity.AcctRes;
import com.ocs.portal.projection.acct.AcctResProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcctResMapper {
  AcctResNameListDto dto (AcctResProjection acctResProjection);

  AcctResDto toDto (AcctRes entity);

  List<AcctResDto> toDtoList (List<AcctRes> entities);

  AcctResDto toAcctResDto (AcctResProjection acctResProjection);
}
