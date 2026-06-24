package com.sts.sinorita.dto.request.accountConfig;

import lombok.Data;

@Data
public class AcctAttrDispOrderDto {
    private Integer attrId;
    private Integer dispOrder;
    private Integer toAttrId;
    private Integer toDispOrder;
}
