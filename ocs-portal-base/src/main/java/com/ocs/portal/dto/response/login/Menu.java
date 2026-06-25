package com.ocs.portal.dto.response.login;

import lombok.Data;

@Data
public class Menu{
    private Long privId;
    private String privName;
    private String url;
    private String comments;
    private String iconUrl;
    private String addStatus;
    private String editStatus;
    private String deleteStatus;
    private String readStatus;
}
