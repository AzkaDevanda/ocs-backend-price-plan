package com.ocs.portal.dto.request.accountConfig;

import lombok.Data;

@Data
public class AcctAttrValueRequestDto {
    private Integer attrId;
    private String attrValue;
    private Integer dispOrder;
    private Integer spId;
}
