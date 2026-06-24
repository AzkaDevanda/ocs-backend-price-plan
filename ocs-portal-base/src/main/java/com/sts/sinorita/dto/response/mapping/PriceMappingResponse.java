package com.sts.sinorita.dto.response.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMappingResponse {
    private int priceId;
    private String priceName;
    private String acctItemTypeName;
    private String valueString;
    private int price;
    private String reAttrName;
}
