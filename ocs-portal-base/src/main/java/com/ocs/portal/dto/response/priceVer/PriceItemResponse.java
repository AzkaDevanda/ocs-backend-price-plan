package com.ocs.portal.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceItemResponse {
    private Character reType;
    private Long priceId;
    private Integer subBalTypeId;
    private String priceName;
    //    private String valueString;
//    private Integer rum;
//    private String acctResName;
    private String acctItemTypeName;
    //    private String reAttrName;
    private String calculateUnit;
}
