package com.ocs.portal.projection.acct;

import java.math.BigDecimal;

public interface AcctItemTypeProjection {
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
    Integer getChild();
}
