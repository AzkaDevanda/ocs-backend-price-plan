package com.sts.sinorita.projection.logManagement;

public interface LogManagementProjection {
    Integer getParam();
    String getParamName();
    String getCurrentValue();
    String getComments();
    String getMask();
    Integer getSpId();
}
