package com.sts.sinorita.svc.role.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppDto {
    private static final long serialVersionUID = 9122850949682850303L;

    private Long appId;

    private String comments;

    private Long spId = Long.valueOf(0L);

    private String appCode;

    private String iconPath;

    private String appName;

    private String appUrl;

    private String state;

    private LocalDate stateDate;

}
