package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctItemTypeRequestDto {

    private Integer acctItemTypeId;
    private Integer acctResId;
    private Integer parentId;
    private Integer exchangeItemTypeId;
    private String acctItemTypeName;
    private String comments;
    private String acctItemTypeCode;
    private Character usageType;
    private Integer spId;
    private Character gstType;
    private Character feeType;
    private Character zeroFeePrintFlag;
    private Integer defaultTaxItemTypeId;
    private Character feeClass;
    private Integer billPriority;
    private Integer acctItemGroupId;
    private String billItemType;

    private Integer taxAcctItemTypeId;
    private Integer discountAcctItemTypeId;
    private Integer taxApplyId;
}
