package com.sts.sinorita.svc.role.dto.response;

import com.sts.sinorita.svc.role.dto.request.roles.PrivDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserPrivDto extends PrivDto implements Serializable {
    private static final long serialVersionUID = -176194372436237326L;

    private Long userId;

    private String userName;
}
