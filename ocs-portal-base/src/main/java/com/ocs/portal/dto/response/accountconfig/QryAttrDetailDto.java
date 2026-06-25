package com.ocs.portal.dto.response.accountconfig;

import lombok.Data;

@Data
public class QryAttrDetailDto {
    private Integer baseAttrId;
    private String inputType;
    private String nullable;
    private String comments;
    private String defaultValue;
    private String valueScript;
    private Integer textAttrId;
    private String dataType;
    private String mask;
    private String ruleScript;
    private String editable;
    private String exceptionMessage;
    private String minValue;
    private String maxValue;
}
