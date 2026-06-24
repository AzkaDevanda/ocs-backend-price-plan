package com.sts.sinorita.projection.pricePlan.trigger;

import java.time.LocalDate;

public interface BalTriggerProjection {
    Integer getTriggerId();

    Integer getOfferVerId();

    Integer getOfferId();

    String getOfferName();

    Integer getAcctResId();

    String getAcctResIdList();

    Character getTriggerType();

    String getTriggerTypeName();

    LocalDate getEffDate();

    Character getState();

    LocalDate getStateDate();

    LocalDate getExpDate();

    String getIsLimit();

    String getDestination();
}
