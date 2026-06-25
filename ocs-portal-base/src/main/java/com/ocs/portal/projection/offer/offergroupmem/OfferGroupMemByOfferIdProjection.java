package com.ocs.portal.projection.offer.offergroupmem;

public interface OfferGroupMemByOfferIdProjection {

    Integer getOfferGroupMemId();

    Integer getOfferGroupId();

    Integer getOfferId();

    Integer getAgreementPeriod();

    Character getTimeUnit();

    Character getDefaultFlag();

    String getOfferGroupName();

    Character getGroupType();

    Character getShareFlag();

    Integer getUpperLimit();

    Integer getLowerLimit();

    Integer getDefaultNum();

}
