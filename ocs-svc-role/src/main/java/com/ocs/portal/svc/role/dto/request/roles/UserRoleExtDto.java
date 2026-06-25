package com.ocs.portal.svc.role.dto.request.roles;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRoleExtDto extends UserRoleDto {
    private static final long serialVersionUID = -6483593349736563966L;

    private Long userRoleTimes;

    private Long staffRoleTimes;

    private LocalDate updateDate;

    private LocalDate createdDate;
}
