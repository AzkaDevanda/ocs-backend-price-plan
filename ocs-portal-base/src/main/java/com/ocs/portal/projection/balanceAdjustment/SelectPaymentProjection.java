package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SelectPaymentProjection {
  Long getPaymentId ();

  Long getReversedPaymentId ();

  Long getPaymentMethodId ();

  String getVoucher ();

  Long getSubmitAmount ();

  Long getReturnAmount ();

  String getRefAttr ();

  Long getBankId ();

  String getCheckNbr ();

  String getCheckOwnerName ();

  LocalDate getCheckIssueDate ();

  LocalDate getCheckExpDate ();

  String getScratchCardPwd ();

  String getPrefix ();

  String getAccNbr ();

  LocalDateTime getPaymentDate ();

  Integer getSpId ();

  Long getOriAcctResId ();

  Long getDestAcctResId ();

  Long getDestAmount ();
}
