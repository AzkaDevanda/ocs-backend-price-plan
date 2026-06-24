package com.sts.sinorita.svc.role.dto.request.job;

import lombok.Data;

@Data
public class JobRoleHisDto extends JobRoleDto {
    private Long recId;
    private String operatorType;

}
