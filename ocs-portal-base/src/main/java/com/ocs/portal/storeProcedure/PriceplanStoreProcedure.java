package com.ocs.portal.storeProcedure;

public interface PriceplanStoreProcedure {
    void callCopyPricePlanVer(Long srcId, Long destId, String prefix, String postfix);
}