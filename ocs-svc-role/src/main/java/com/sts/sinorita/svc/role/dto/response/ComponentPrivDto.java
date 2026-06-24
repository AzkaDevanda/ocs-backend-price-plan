package com.sts.sinorita.svc.role.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sts.sinorita.svc.role.dto.request.roles.PrivDto;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentPrivDto extends PrivDto implements Serializable {
    private static final long serialVersionUID = 312037825766058311L;

    private Long menuId;

    private String objId;

    private String menuName;

    private String partyName;

    private Long partyId;

}
