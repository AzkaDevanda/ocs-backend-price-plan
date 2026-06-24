package com.sts.sinorita.svc.role.dto.request.roles;

import lombok.Data;

@Data
public class ProdPortalDto extends PortalDto{

    private Long appId;

    private Long indexId;

    private Long spId;

    private String allowExternalAccess;

}
