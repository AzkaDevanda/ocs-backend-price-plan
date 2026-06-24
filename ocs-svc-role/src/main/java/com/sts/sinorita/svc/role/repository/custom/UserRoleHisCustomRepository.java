package com.sts.sinorita.svc.role.repository.custom;

import com.sts.sinorita.svc.role.dto.request.roles.UserRoleDto;
import com.sts.sinorita.svc.role.dto.request.roles.UserRoleExtDto;

import java.util.List;

public interface UserRoleHisCustomRepository {
    public List<UserRoleExtDto> selectUserRoles(List<UserRoleDto> list);
}
