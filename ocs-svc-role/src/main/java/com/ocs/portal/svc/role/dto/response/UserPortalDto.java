package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPortalDto {
    private static final long serialVersionUID = 5939109482946062173L;

    private String state;

    private LocalDateTime stateDate;

    private Long userId;

    private Long portalId;

    private String portalName;

    private String iconUrl;

    private String url;

    private String comments;
}
