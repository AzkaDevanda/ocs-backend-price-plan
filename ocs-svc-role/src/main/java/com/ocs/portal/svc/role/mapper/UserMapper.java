package com.ocs.portal.svc.role.mapper;

import com.ocs.portal.entity.BfmUser;
import com.ocs.portal.svc.role.dto.response.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "pwd", expression = "java(\"****\")")
    UserDto userDto(BfmUser bfmUser);

    List<UserDto> toListUserDto(List<BfmUser> list);
}
