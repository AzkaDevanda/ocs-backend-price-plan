package com.sts.sinorita.projection.acct;

import java.math.BigDecimal;

public interface AcctItemTypeDetailProjection {
    Integer getAcctItemTypeId();
    Integer getAcctResId();
    Integer getParentId();
    Integer getExchangeItemTypeId();
    String getAcctItemTypeName();
    String getComments();
    String getAcctItemTypeCode();
    Character getUsageType();
    Character getGstType();
    Character getFeeType();
    String getFeeClass();
    Character getZeroFeePrintFlag();
    Integer getBillPriority();
    Integer getAcctItemGroupId();
    Integer getDefaultTaxItemTypeId();
    String getAcctResName();
    Integer getBalType();
    Character getIsCurrency();
    BigDecimal getCreditLimit();
    String getBalTypeName();
    Integer getTaxAcctItemTypeId();
    Integer getDiscountAcctItemTypeId();
    Integer getTaxApplyId();
}
