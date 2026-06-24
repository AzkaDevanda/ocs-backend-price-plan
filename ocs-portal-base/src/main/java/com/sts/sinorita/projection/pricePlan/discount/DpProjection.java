package com.sts.sinorita.projection.pricePlan.discount;

public interface DpProjection {
    int getDpId();
    String getDpName();
    int getPriority();
    String getDpType();
    String getDpTypeName();
    int getPricePlanVerId();
    Character getBillingPlanType();
    String getComments();
}
