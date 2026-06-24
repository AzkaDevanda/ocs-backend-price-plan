package com.sts.sinorita.svc.role.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdUserPrivDto extends UserPrivDto implements Serializable {
    private static final long serialVersionUID = -1283591017819798004L;

    private String dataPrivId;

    private String privValue;

    private Long spId;

    private String userCode;

    private String ownedType;

    private String privLevel;

    private Long menuId;
}
