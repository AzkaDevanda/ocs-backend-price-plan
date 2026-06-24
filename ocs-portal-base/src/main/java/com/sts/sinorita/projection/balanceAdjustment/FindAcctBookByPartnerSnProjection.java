package com.sts.sinorita.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface FindAcctBookByPartnerSnProjection {
  Long getAcctBookId();

  Long getAcctId();

  Long getAcctResId();

  String getAcctBookType();

  LocalDateTime getCreatedDate();

  Long getPreBalance();

  LocalDateTime getPreExpDate();

  Long getCharge();

  Long getBillId();

  String getPartyType();

  String getPartyCode();

  Long getPreSuttleBal();

  Long getSeconds();

  Long getBalId();

  Long getContactChannelId();

  Long getSpId();

}
