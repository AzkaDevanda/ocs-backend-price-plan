package com.ocs.portal.svc.role.dto.request.job;


import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRoleDto {
    private static final long serialVersionUID = -1745096756876721259L;

    public Long jobRoleId;

    public Long jobId;

    public Long roleId;

    public String state;

    public LocalDate stateDate;

    private LocalDate updateDate;

    private LocalDate createdDate;

    public Long spId;
}
