package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sts.sinorita.dto.AsynCallDto;
import com.sts.sinorita.entity.AsynCall;

@Mapper(componentModel = "spring")
public interface AsynCallMapper {

  @Mapping(ignore = true, target = "asynCalId")
  AsynCall toEntity(AsynCallDto asynCallDto);

}
