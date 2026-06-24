package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.ExtAttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalanceChangeTriggerDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ExtAttrBalanceChangeTrigger;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.BalanceChangeTriggerDict;
import com.sts.sinorita.dto.response.balanceAdjustment.SelectBalListFilterExpireExceptRefillYResponseDto;
import com.sts.sinorita.entity.mdbtt.BalEntity;

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
