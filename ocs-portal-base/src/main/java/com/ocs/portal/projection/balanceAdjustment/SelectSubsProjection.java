package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface SelectSubsProjection {
  Long getSubsId ();

  String getPrefix ();

  String getAccNbr ();

  Long getCustId ();

  Long getUserId ();

  Long getAgentId ();

  Long getOrgId ();

  Long getAreaId ();

  Date getUpdateDate ();

  Long getRoutingId ();

  Long getDefLangId ();

  String getPpsPwd ();

  String getComments ();

  Long getSpId ();

  Long getAcctId ();

  Long getPpsCreditLimit ();

  String getNeedUpload ();

  Long getCreateStaffId ();

  String getSecondNbr ();
}
