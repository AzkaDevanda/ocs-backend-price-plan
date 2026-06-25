package com.ocs.portal.projection.acct;

import java.time.LocalDateTime;

public interface SelectAcctDtoByAcctIdProjection {
  Long getAcctId();

  Long getCustId();

  String getAcctNbr();

  Long getBillingCycleTypeId();

  String getPaymentType();

  Long getBankId();

  String getBankAcctNbr();

  String getBankAcctName();

  LocalDateTime getCreatedDate();

  LocalDateTime getUpdateDate();

  String getState();

  LocalDateTime getStateDate();

  String getPostpaid();

  Long getRoutingId();

  Long getBillFormatId();

  Long getSpId();
}
