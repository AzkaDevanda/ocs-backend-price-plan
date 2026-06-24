package com.sts.sinorita.projection.trigger;

public interface FindBwfCondGroupByStepIdProjection {
    Long getCondGroupId();
    Integer getStepId();
    Integer getMatchSortAttrGroupId();
    Integer getSeq();
    String getReAttr();
    String getFunction();
    String getParam1();
    String getParam2();
    String getSortOperator();
    String getOperand();
    String getIsConst();
    Integer getRReAttr();
    String getRFunction();
    String getRParam1();
    String getRParam2();
    Integer getZoneId();
    String getFunctionScript();
    String getRFunctionScript();
    String getReType();
    String getReAttrName();
    String getRReType();
    String getRReAttrName();
//    String getSortOperatorName();
//    String getSortOperatorComments();
    Integer getZoneId2(); // F.ZONE_ID (duplikat kolom, beri nama beda)
    Integer getParentZoneId();
    String getZoneName();
    String getZoneItemComments();
    Integer getZoneMapId();
    String getZoneCode();
}
