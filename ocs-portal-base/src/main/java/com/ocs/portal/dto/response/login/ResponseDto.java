package com.ocs.portal.dto.response.login;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

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
