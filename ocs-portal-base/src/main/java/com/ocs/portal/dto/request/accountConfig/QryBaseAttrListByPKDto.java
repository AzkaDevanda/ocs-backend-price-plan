package com.ocs.portal.dto.request.accountConfig;

import com.ocs.portal.projection.accountConfig.QryAttrValueProjection;
import lombok.Data;

import java.util.List;

@Data
public class QryBaseAttrListByPKDto {
    private Long attrId;
    private String attrName;
    private String attrCode;
    private String csrVisible;
    private String inputType;
    private String nullable;
    private String comments;
    private String defaultValue;
    private String valueScript;
    private String inputTypeName;
    private String dataType;
    private String mask;
    private String ruleScript;
    private String editable;
    private String exceptionMessage;
    private String minValue;
    private String maxValue;
    private String dataTypeName;
    private List<QryAttrValueProjection> attrValueList;
}
