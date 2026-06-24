package com.sts.sinorita.projection.accountConfig;

import java.math.BigDecimal;

public interface QryInstalmentItemByTypeIDProjection {
    Long getInstalmentTypeId();

    Long getSeq();

    BigDecimal getItemPercent();

    Integer getRepeatTime();

    String getActionType(); // nilai konstan 'X' dari query

    BigDecimal getFeePercent();
}
