package com.sts.sinorita.projection.pricePlan.price;

public interface QryAcmRefProjection {

    Integer getAcmRefId();

    Integer getPriceVerId();

    Integer getResourceId();

    String getResourceName();

    Character getAdjustMethod();

    Integer getAcmTimeSpanId();

    Integer getRate();

    Long getRum();

    Long getEffValue();

    Long getExpValue();

    Integer getAcmTimeSpanPriority();

}
