package com.ocs.portal.dto.response.accountconfig;

import lombok.Data;

@Data
public class QryAttrValueDto {
    private Integer baseAttrId;
    private Integer attrValueId;
    private String valueMark;
    private String value;
    private Integer parentAttrValueId;
    private Integer parentAttrId;
    private String attrName;
}
