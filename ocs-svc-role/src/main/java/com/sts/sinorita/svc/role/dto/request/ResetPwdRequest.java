package com.sts.sinorita.svc.role.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResetPwdRequest {
    private String oldPwd;
    private String userName;
    private String userCode;
    private String newPwd;
}
