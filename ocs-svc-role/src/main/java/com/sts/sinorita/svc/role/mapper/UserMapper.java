package com.sts.sinorita.svc.role.mapper;

import com.sts.sinorita.entity.BfmUser;
import com.sts.sinorita.svc.role.dto.response.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "pwd", expression = "java(\"****\")")
    UserDto userDto(BfmUser bfmUser);

    List<UserDto> toListUserDto(List<BfmUser> list);
}
