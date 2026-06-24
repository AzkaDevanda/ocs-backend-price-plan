package com.sts.sinorita.dto.response.accountconfig;

import lombok.Data;

import java.util.List;

@Data
public class AccountFeatureDto {
    private Integer attrId;
    private String attrCode;
    private String attrName;
    private Character attrType;
    private Character csrVisible;
    private String inputType;
    private String nullable;
    private String comments;
    private String defaultValue;
    private Integer dispOrder;
    private Integer spId;
    private String attrValue;

    // buat dropdown value list
    private List<AcctValuesListDto> acctValuesListDto;
}
