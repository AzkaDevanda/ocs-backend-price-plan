package com.sts.sinorita.projection.balanceAdjustment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SelectLastRecentValidAcctBookProjection {
  Long getAcctBookId ();

  Long getAcctId ();

  Long getAcctResId ();

  String getAcctBookType ();

  LocalDateTime getCreatedDate ();

  Long getPreBalance ();

  LocalDate getPreExpDate ();

  Long getCharge ();

  Long getBillId ();

  String getPartyType ();

  String getPartyCode ();

  Long getPreSuttleBal ();

  Long getSeconds ();

  Long getBalId ();

  Long getContactChannelId ();

  Long getEventPaymentId ();

  Integer getSpId ();
}
