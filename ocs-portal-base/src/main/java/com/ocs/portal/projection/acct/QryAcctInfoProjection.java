package com.ocs.portal.projection.acct;

import java.util.Date;

public interface QryAcctInfoProjection {
  Long getAcctId ();

  Long getCustId ();

  String getAcctNbr ();

  String getAcctName ();

  Long getBillingCycleTypeId ();

  Long getBillFormatId ();

  String getPostpaid ();

  Long getStdAddrId ();

  String getBillAddress ();

  String getPaymentType ();

  Long getPaymentMethodId ();

  Long getBankId ();

  String getBankAcctNbr ();

  String getBankAcctName ();

  Date getCreatedDate ();

  Date getUpdateDate ();

  String getState ();

  Date getStateDate ();

  String getBillingCycleTypeName ();

  String getDefaultFlag ();

  String getPaymentTypeName ();

  String getBankName ();

  String getCustName ();

  String getCertNbr ();

  String getCertTypeName ();

  String getAddress ();

  String getIsProject ();

  Long getRoutingId ();

  String getPaymentMethodName ();

  String getPaymentComments ();

  String getBankCardType ();

  String getIsLock ();

  Long getSpId ();

  Date getAllowModStateDate ();

  String getCustType ();
}
