package com.sts.sinorita.projection.subs;

import java.time.LocalDateTime;

public interface SelectSubsByAcctIdProjection {
  Long getSubsId();

  String getPrefix();

  String getAccNbr();

  Long getCustId();

  Long getUserId();

  Long getAcctId();

  Long getAgentId();

  Long getAreaId();

  LocalDateTime getUpdateDate();

  Long getRoutingId();

  Long getDefLangId();

  String getPpsPwd();

  Long getSpId();

  LocalDateTime getCreatedDate();

  LocalDateTime getCompletedDate();

  String getProdState();

  LocalDateTime getStateDate();

  LocalDateTime getProdUpdateDate();

  LocalDateTime getProdStateDate();

  String getBlockReason();

  LocalDateTime getProdExpDate();

  Long getIndepProdId();
}
