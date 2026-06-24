package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.balanceAdjustment.BalAcctItemTypeDto;
import com.sts.sinorita.entity.BalAcctItemType;
import com.sts.sinorita.projection.balanceAdjustment.SelectBalAcctItemTypeProjection;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalAcctItemTypeMapper {
  com.sts.sinorita.dto.response.balanceAdjustment.BalAcctItemTypeDto toDto(BalAcctItemType entity);

  BalAcctItemType toEntity(BalAcctItemTypeDto dto);

  List<BalAcctItemTypeDto> toDtoList(List<BalAcctItemType> entities);

  BalAcctItemTypeDto toBalAcctItemTypeDto(SelectBalAcctItemTypeProjection projection);

}
