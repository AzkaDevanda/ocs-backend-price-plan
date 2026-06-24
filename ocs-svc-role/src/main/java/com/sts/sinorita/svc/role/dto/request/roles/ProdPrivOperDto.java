package com.sts.sinorita.svc.role.dto.request.roles;

import lombok.Data;

@Data
public class ProdPrivOperDto extends PrivOperDto{
    private String privLevel;

    private Long menuId;

    private String ownType;
}
