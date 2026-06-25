package com.ocs.portal.projection.offer.offerapplyorg;

import java.time.LocalDate;

public interface OfferApplyOrgProjection {
    Integer getOfferId();

    Integer getOrgId();

    Integer getSpId();

    Character getExcludeFlag();

    Integer getParentOrgId();

    Integer getAreaId();

    String getOrgName();

    String getConditionName();

    String getOrgType();

    Character getState();

    LocalDate getStateDate();

    String getOrgCode();
}
