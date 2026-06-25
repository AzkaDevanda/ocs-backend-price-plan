package com.ocs.portal.dto.request;

import lombok.Data;

@Data
public class TariffTemplateParamDto {
    private String paramType;

    private Long paramId;

    private String paramName;

    private String paramCode;

    private String defValue;

    public String isGlobal;
}
