package com.sts.sinorita.projection.pricePlan.trigger;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AcmTriggerProjection {

    Integer getTriggerId();

    LocalDateTime getEffDate();

    LocalDateTime getExpDate();

    Integer getAccumulationTypeId();

    String getAccumulationTypeName();

    Character getTriggerModeId();

    String getTriggerModeName();

    String getState();

    LocalDateTime getStateDate();

    Character getDestination();
}
