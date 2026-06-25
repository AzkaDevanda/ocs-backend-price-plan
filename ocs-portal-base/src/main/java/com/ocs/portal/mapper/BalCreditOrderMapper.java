package com.ocs.portal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ocs.portal.dto.request.balanceAdjustment.BalCreditOrderDto;
import com.ocs.portal.dto.request.balanceAdjustment.BalanceChangeTriggerDto;
import com.ocs.portal.entity.BalCreditOrder;

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
