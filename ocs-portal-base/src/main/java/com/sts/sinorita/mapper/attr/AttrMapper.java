package com.sts.sinorita.mapper.attr;

import com.sts.sinorita.dto.AcctAttrValueDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AttrDto;
import com.sts.sinorita.dto.response.attr.AttrListByCatgResponseDto;
import com.sts.sinorita.projection.acct.SelectAcctAttrValueProjection;
import com.sts.sinorita.projection.attr.AttrListByCatgProjection;
import com.sts.sinorita.projection.attr.SelectAttrByCodeProjection;
import com.sts.sinorita.projection.balanceAdjustment.GetAttrByCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttrMapper {
  AttrListByCatgResponseDto toAttrListByCatgResponseDto(AttrListByCatgProjection projection);

  AttrDto getAttrByCodeToAttrDto(GetAttrByCodeProjection projection);

  AttrDto selectAttrByCodeToAttrDto(SelectAttrByCodeProjection projection);

  AcctAttrValueDto toAcctAttrValueDto(SelectAcctAttrValueProjection projection);

}
