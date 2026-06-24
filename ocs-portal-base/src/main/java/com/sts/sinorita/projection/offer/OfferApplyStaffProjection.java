package com.sts.sinorita.projection.offer;

import java.time.LocalDate;

public interface OfferApplyStaffProjection {

    Integer getOfferId();

    Integer getStaffId();

    Integer getSpId();

    Character getExcludeFlag();

    String getUserCode();

    String getStaffName();

    Character getState();

    LocalDate getSateDate();

    Integer getUserId();
}
