package com.ocs.portal.projection.accountConfig;

public interface QryDDParamProjection {
    Integer getPaymentMethodId();
    Integer getDaysBefExtra();
    String getSpIban();
    Integer getReIssueDelay();
    Integer getCloseMandateLimit();
}
