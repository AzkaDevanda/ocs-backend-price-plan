package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcctItemTypeUpdateDto {
    private Integer acctItemTypeId;
    private Integer acctResId;
    private Integer parentId;
    private Integer exchangeItemTypeId;
    private String acctItemTypeName;
    private String comments;
    private String acctItemTypeCode;
    private Character usageType;
    private Character gstType;
    private Character feeType;
    private String feeClass;
    private Character zeroFeePrintFlag;
    private Integer billPriority;
    private Integer acctItemGroupId;
    private Integer defaultTaxItemTypeId;
    private String acctResName;
    private Integer balType;
    private Character isCurrency;
    private BigDecimal creditLimit;
    private String balTypeName;
    private Integer taxAcctItemTypeId;
    private Integer discountAcctItemTypeId;
    private Integer taxApplyId;
}
