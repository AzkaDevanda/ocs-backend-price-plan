package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

@Data
public class ParamsDto {
    private static final long serialVersionUID = 7727467259804075293L;

    private Long param;

    private String paramName;

    private String currentValue;

    private String comments;

    private String mask;

}
