package com.sts.sinorita.svc.role.dto.request.roles;

import lombok.Data;

@Data
public class UserRoleHisDto extends UserRoleExtDto{
    private Long userRoleHisId;

    private String operatorType;

}
