package com.ocs.portal.svc.role.dto.request.roles;

import lombok.Data;

@Data
public class DirMenusDto {
    private Long portalId;

    private String type;

    private Long partyId;

    private String partyName;

    private String url;

    private Long parentId;

    private Long seq;

    private String isHold;
}
