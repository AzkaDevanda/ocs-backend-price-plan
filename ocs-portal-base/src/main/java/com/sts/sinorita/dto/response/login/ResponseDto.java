package com.sts.sinorita.dto.response.login;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseDto {
    private String username;
    private String userCode;
    private String token;
    private String role;
    private List<Menu> menus;
    private long expired;
    private Integer userId;
    private Object jobs;
    private String forceLogin = "";
    private String RefreshToken;

    public String getForceLogin() {
        return forceLogin == null ? "" : forceLogin;
    }
}
