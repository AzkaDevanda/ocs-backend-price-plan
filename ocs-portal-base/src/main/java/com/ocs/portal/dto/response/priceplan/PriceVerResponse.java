package com.ocs.portal.dto.response.priceplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class
PriceVerResponse {
    private String priceName;
//    private Integer acctItemTypeId;
    private String acctItemTypeName;
    private Long calculateType;
}
