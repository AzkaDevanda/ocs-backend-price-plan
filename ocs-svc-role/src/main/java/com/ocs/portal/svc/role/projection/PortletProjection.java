package com.ocs.portal.svc.role.projection;

import java.time.LocalDate;

public interface PortletProjection {

    Long getPortletId();
    String getPortletName();
    Long getTypeId();
    String getDefaultTitle();
    String getShowHeader();
    String getSettable();
    String getCollapsable();
    String getRefreshable();
    String getMaxable();
    String getClosable();
    String getState();
    String getIcon();
    String getDefaultParam();
    LocalDate getStateDate();
    Long getPrivId();
    String getUrl();
    String getDefaultWidth();
    String getDefaultHeight();
    String getViewType();
    String getIsDrawable();
}
