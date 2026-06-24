package com.sts.sinorita.svc.role.dto.response;

import com.sts.sinorita.svc.role.dto.request.roles.RolePortalDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RolePortalHisDto extends RolePortalDto {
    private Long recId;

    private String operatorType;

    private LocalDateTime updateDate;

    private LocalDateTime createdDate;
}
