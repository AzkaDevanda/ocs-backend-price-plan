package com.ocs.portal.projection.trigger;

public interface SortFunctionProjection {

    String getFunction();
    String getComments();
    Integer getParamNum();
    String getParam1Name();
    String getParam1ValueType();
    String getParam1Desc();
    String getParam1ValueScript();
    String getParam2Name();
    String getParam2ValueType();
    String getParam2Desc();
    String getParam2ValueScript();
    String getUsageFlag();
    String getFunctionTypeFlag();
    String getParam1ValueTypeName();
    String getParam2ValueTypeName();
}
