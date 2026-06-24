package com.sts.sinorita.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface QryOriNoteProjection {
  Long getNoteId ();

  Long getPaymentMethodId ();

  Long getBankId ();

  String getVoucher ();

  Long getCharge ();

  String getCheckNbr ();

  String getCheckOwnerName ();

  LocalDateTime getCheckIssueDate ();

  LocalDateTime getCheckExpDate ();

  String getScratchCardPwd ();

  Long getCustId ();

  Long getAcctId ();
}
