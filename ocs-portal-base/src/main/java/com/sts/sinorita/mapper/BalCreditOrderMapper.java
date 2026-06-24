package com.sts.sinorita.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sts.sinorita.dto.request.balanceAdjustment.BalCreditOrderDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalanceChangeTriggerDto;
import com.sts.sinorita.entity.BalCreditOrder;

@Mapper(componentModel = "spring")
public interface BalCreditOrderMapper {

  BalCreditOrder toEntity(BalCreditOrderDto dto);

  @Mapping(target = "balCreditOrderId", source = "balCreditOrderId")
  @Mapping(target = "acctBookId", source = "triggerDto.acctBookId")
  @Mapping(target = "spId", source = "triggerDto.spId")
  @Mapping(target = "subsId", source = "source.subsId")
  @Mapping(target = "blockReason", source = "source.blockReason")
  BalCreditOrder toEntity(BalCreditOrderDto source, BalanceChangeTriggerDto triggerDto, Long balCreditOrderId);

}
