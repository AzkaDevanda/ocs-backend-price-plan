package com.sts.sinorita.projection.acct;

public interface PaymentMethodProjection {
    Integer getPaymentMethodId();
    String getPaymentMethodName();
    String getComments();
    Character getPaymentType();
    String getPaymentTypeName();
    Integer getSpId();
    String getPaymentMethodCode();
    Character getSystemReserved();
    String getGroupType();
}
