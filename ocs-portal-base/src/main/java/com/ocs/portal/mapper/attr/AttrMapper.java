package com.ocs.portal.mapper.attr;

import com.ocs.portal.dto.AcctAttrValueDto;
import com.ocs.portal.dto.request.balanceAdjustment.AttrDto;
import com.ocs.portal.dto.response.attr.AttrListByCatgResponseDto;
import com.ocs.portal.projection.acct.SelectAcctAttrValueProjection;
import com.ocs.portal.projection.attr.AttrListByCatgProjection;
import com.ocs.portal.projection.attr.SelectAttrByCodeProjection;
import com.ocs.portal.projection.balanceAdjustment.GetAttrByCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttrMapper {
  AttrListByCatgResponseDto toAttrListByCatgResponseDto(AttrListByCatgProjection projection);

  AttrDto getAttrByCodeToAttrDto(GetAttrByCodeProjection projection);

  AttrDto selectAttrByCodeToAttrDto(SelectAttrByCodeProjection projection);

  AcctAttrValueDto toAcctAttrValueDto(SelectAcctAttrValueProjection projection);

}
