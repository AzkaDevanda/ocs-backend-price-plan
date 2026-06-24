package com.sts.sinorita.svc.role.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdComponentPrivDto extends ComponentPrivDto implements Serializable {

    private static final long serialVersionUID = 312037825766058311L;

    private String privLevel;

    private String effectType;

    private String ownType;

    private long roleCount;
}
