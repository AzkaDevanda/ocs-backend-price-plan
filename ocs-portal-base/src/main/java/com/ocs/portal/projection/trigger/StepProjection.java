package com.ocs.portal.projection.trigger;

import java.time.LocalDateTime;

public interface StepProjection {
    Long getInputNodeId();
    Long getNodeId();
    Long getStepId();
    LocalDateTime getEffDate();
    LocalDateTime getExpDate();
    String getSortRuleName();
    String getComments();
    Integer getExecOrder();
    Long getOutputNodeId();
    String getInputNodeName();
    Long getWorkflowId();
    String getOutputNodeName();
}
