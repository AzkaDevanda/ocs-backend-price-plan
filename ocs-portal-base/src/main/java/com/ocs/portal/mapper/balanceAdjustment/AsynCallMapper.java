package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ocs.portal.dto.AsynCallDto;
import com.ocs.portal.entity.AsynCall;

@Mapper(componentModel = "spring")
public interface AsynCallMapper {

  @Mapping(ignore = true, target = "asynCalId")
  AsynCall toEntity(AsynCallDto asynCallDto);

}
