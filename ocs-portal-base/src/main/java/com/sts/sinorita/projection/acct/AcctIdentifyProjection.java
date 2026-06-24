package com.sts.sinorita.projection.acct;

import java.util.Date;

public interface AcctIdentifyProjection {
    Long getAcctIdentifyId();

    String getIdentifyType();

    String getIdentifyValue();

    Long getAcctId();

    Date getCreatedDate();

    Date getUpdateDate();

    String getState();

    Long getSpId();

    Long getRoutingId();
}
