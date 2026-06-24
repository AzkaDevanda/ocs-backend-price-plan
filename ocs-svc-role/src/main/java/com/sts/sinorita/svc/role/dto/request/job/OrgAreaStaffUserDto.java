package com.sts.sinorita.svc.role.dto.request.job;

import lombok.Data;

@Data
public class OrgAreaStaffUserDto {
    private static final long serialVersionUID = -3831332240042396305L;

    public Long staffJobId;

    public Long staffOrgId;

    public Long staffId;

    public String staffName;

    public String orgName;

    public Long orgId;

    public String areaName;

    public String areaCode;

    public Long areaId;

    public String userName;

    public String userCode;

    public Long userId;

    public String staffCode;
}
