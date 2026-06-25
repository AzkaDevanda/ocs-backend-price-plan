package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.response.balanceAdjustment.BalAcctItemTypeDto;
import com.ocs.portal.entity.BalAcctItemType;
import com.ocs.portal.projection.balanceAdjustment.SelectBalAcctItemTypeProjection;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalAcctItemTypeMapper {
  BalAcctItemTypeDto toDto(BalAcctItemType entity);

  BalAcctItemType toEntity(com.ocs.portal.dto.request.balanceAdjustment.BalAcctItemTypeDto dto);

  List<com.ocs.portal.dto.request.balanceAdjustment.BalAcctItemTypeDto> toDtoList(List<BalAcctItemType> entities);

  com.ocs.portal.dto.request.balanceAdjustment.BalAcctItemTypeDto toBalAcctItemTypeDto(SelectBalAcctItemTypeProjection projection);

}
