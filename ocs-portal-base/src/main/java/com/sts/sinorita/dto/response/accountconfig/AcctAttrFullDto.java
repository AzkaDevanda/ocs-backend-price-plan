package com.sts.sinorita.dto.response.accountconfig;

import lombok.Data;

import java.util.List;

@Data
public class AcctAttrFullDto {
    private String attrId;
    private String attrCode;
    private String nullable;
    private String csrVisible;
    private String defaultValue;
    private List<AcctValuesListDto> attrValueList;
    private String inputType;
    private String attrValue;
    private String dispOrder;
    private Integer spId;
    private String attrName;
    private Character attrType;
    private String comments;
}
