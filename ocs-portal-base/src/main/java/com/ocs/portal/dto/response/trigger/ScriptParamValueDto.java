package com.ocs.portal.dto.response.trigger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptParamValueDto {
    private String paramId;
    private String type;
    private String dataType;
    private String paramValue;

}
