package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.ExtAttrDto;
import com.ocs.portal.dto.request.balanceAdjustment.BalanceChangeTriggerDto;
import com.ocs.portal.dto.request.balanceAdjustment.ExtAttrBalanceChangeTrigger;
import com.ocs.portal.dto.request.balanceAdjustment.adjust.BalanceChangeTriggerDict;
import com.ocs.portal.dto.response.balanceAdjustment.SelectBalListFilterExpireExceptRefillYResponseDto;
import com.ocs.portal.entity.mdbtt.BalEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalMapper {
  // SelectBalListFilterExpireExceptRefillYResponseDto
  // toSelectBalListFilterExpireExceptRefillYResponseDto
  // (SelectBalListFilterExpireExceptRefillYProjection projection);

  // Define method untuk SINGLE object (untuk .map())
  @Mapping(target = "balAcctItemType", ignore = true)
  @Mapping(target = "acctRes", ignore = true)
  @Mapping(source = "acctResId", target = "acctResId")
  SelectBalListFilterExpireExceptRefillYResponseDto toSelectBalListFilterExpireExceptRefillYResponseDto(BalEntity bal);

  // OPTIONAL: Define method untuk List (jika mau pakai langsung tanpa stream)
  List<SelectBalListFilterExpireExceptRefillYResponseDto> toDtoList(List<BalEntity> balances);

  ExtAttrDto toExtAttrDto(ExtAttrBalanceChangeTrigger value);

  BalanceChangeTriggerDto toBalanceChangeTriggerDto(BalanceChangeTriggerDict dict);

  

}
