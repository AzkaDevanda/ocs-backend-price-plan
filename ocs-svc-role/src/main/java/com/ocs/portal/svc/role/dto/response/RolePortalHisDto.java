package com.ocs.portal.svc.role.dto.response;

import com.ocs.portal.svc.role.dto.request.roles.RolePortalDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RolePortalHisDto extends RolePortalDto {
    private Long recId;

    private String operatorType;

    private LocalDateTime updateDate;

    private LocalDateTime createdDate;
}
