package com.ocs.portal.svc.role.repository.custom;

import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;

import java.util.List;

public interface UserRoleHisCustomRepository {
    public List<UserRoleExtDto> selectUserRoles(List<UserRoleDto> list);
}
