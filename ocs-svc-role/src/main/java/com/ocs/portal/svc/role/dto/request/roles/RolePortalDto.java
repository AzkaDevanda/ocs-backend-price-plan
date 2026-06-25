package com.ocs.portal.svc.role.dto.request.roles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePortalDto {
    private static final long serialVersionUID = 8842533086302053249L;

    private Long roleId;

    private Long portalId;

    private String state;

    private LocalDate stateDate;

    private String portalName;

    private String iconUrl;

    private String url;

    private String comments;

    private String roleName;
}
