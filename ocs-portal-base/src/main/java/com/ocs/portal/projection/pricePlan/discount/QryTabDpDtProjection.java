package com.ocs.portal.projection.pricePlan.discount;

public interface QryTabDpDtProjection {
    Integer getDpId();
    Integer getSeqNo();
    Character getDisctCalcMethod();
    String getSVal();
    String getEVal();
    String getRefValue();
    String getRefCellValue();
    String getRefFloorValue();
    Integer getSpId();
    String getShowVal();
}
