package com.ocs.portal.svc.role.dto.request.roles;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDto {

    private static final long serialVersionUID = -8606804977642858935L;

    private Long userId;

    private Long roleId;

    String roleName;

    String comments;

    String roleCode;

    String isLocked;

    private String userCode;

    private Long spId;
}
