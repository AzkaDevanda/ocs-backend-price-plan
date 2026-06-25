package com.ocs.portal.dto.request;

import lombok.Data;
import org.springframework.data.repository.query.Param;

@Data
public class AcctItemTypeSearchRequestDto {
    Integer acctItemTypeId;
    Integer acctResId;
    Integer nullFlag;
    Integer parentId;
    Integer priceVerId;
    Integer priceVerIdExists;
    Integer priceVerIdEx;
    Integer curPriceId;
    Integer spId;
    String usageType;
    Integer defaultAcctResId;
    String acctItemTypeName;
}
