package com.ocs.portal.projection.accountConfig;

public interface QryInstalmentType1Projection {
    Long getInstalmentTypeId();

    String getInstalmentTypeName();

    Integer getFirstPay();

    Long getRepeatTimes();

    String getComments();

    Integer getFeePercents();
}
