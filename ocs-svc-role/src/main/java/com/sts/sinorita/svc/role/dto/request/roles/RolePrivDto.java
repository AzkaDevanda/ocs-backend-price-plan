package com.sts.sinorita.svc.role.dto.request.roles;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RolePrivDto extends PrivDto{
    private static final long serialVersionUID = 6743170674372864369L;

    private Long partyId;

    private Long roleId;
}
