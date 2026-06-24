package com.sts.sinorita.dto.response.accountconfig;

import lombok.Data;

@Data
public class QryAttrListByCatgDto {
    private Integer attrId;
    private String attrName;
    private String attrType;
    private Integer objAttrId;
    private String attrCode;
    private String csrVisible;
    private String instantiatable;
    private String configVisible;
    private String editable;
}
