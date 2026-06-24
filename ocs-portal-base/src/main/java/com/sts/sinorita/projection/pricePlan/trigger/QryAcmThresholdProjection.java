package com.sts.sinorita.projection.pricePlan.trigger;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface QryAcmThresholdProjection {

    Integer getAcmThresholdId();

    Integer getTriggerId();

    Long getValue();

    Long getInterval();

    Integer getReAttr();

    String getReAttrName();

    Integer getRatio();

    Character getTouchPcrf();

    String getTriggerMode();

    Long getAcmBilShockRuleId();

    String getRuleName();

    Integer getOffAttr();

    Integer getOnOffAttr();

}
