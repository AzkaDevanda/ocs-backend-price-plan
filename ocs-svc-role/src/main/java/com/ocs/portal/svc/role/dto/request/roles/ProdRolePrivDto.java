package com.ocs.portal.svc.role.dto.request.roles;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProdRolePrivDto extends RolePrivDto{

    private static final long serialVersionUID = -3565389814577190008L;

    private String dataPrivId;

    private Long spId;

    private String privValue;

    private String ownedType;

    private String roleName;

    private String roleCode;

    private String autoOpenMenu;

    private Long menuId;

    private String menuType;

    private String privLevel;
    private Long privId;

    private String url;
    private String comments;
    private String iconUrl;

    private String addStatus;
    private String editStatus;
    private String readStatus;
    private String deleteStatus;
}
