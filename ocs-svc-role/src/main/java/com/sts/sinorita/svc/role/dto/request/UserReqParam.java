package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

@Data
public class UserReqParam {
    private String userName;
    private String userCode;
    private String state;
    private String isLock;
}
