package com.sts.sinorita.projection.accountConfig;

public interface QryDDParamProjection {
    Integer getPaymentMethodId();
    Integer getDaysBefExtra();
    String getSpIban();
    Integer getReIssueDelay();
    Integer getCloseMandateLimit();
}
