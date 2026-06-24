package com.sts.sinorita.projection.accountConfig;

public interface QryDepositType {
    Integer getDepositTypeId();
    String getName();
    String getComments();
    Long getCharge();
    Integer getSpId();
    String getDepositTypeCode();
    String getRefundable();
    String getTransCredit();
    Integer getCheckDuration();
}
