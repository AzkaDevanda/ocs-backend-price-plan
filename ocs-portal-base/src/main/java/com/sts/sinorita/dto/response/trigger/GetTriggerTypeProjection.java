package com.sts.sinorita.dto.response.trigger;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"triggerType", "triggerTypeName", "comments"})
public interface GetTriggerTypeProjection {
    Character getTriggerType();
    String getTriggerTypeName();
    String getComments();
}
