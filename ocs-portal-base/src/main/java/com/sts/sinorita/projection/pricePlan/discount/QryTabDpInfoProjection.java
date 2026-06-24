package com.sts.sinorita.projection.pricePlan.discount;

public interface QryTabDpInfoProjection {
    Integer getDpId();
    String getTabDpType();
    Integer getRefDisctObjId();
    Integer getAcctItemTypeId();
    String getDistributeMethod();
    Integer getTabDpCondGrpId();
    Integer getCalcDisctObjId();
    Integer getApplyDisctObjId();
    String getNegativeFlag();
    Integer getSpId();

    String getAcctItemTypeName();
    String getAcctItemTypeCode();
    Integer getAcctResId();

    Integer getRefObjIdentityId();
    String getRefDisctObjName();
    String getRefDisctObjType();
    Integer getRefTabDpCondGrpId();
    Integer getRefGstSeq();
    String getRefMemberAlias();

    Integer getCalcObjIdentityId();
    String getCalcDisctObjName();
    String getCalcDisctObjType();
    Integer getCalcTabDpCondGrpId();
    Integer getCalcGstSeq();
    String getCalcMemberAlias();

    Integer getApplyObjIdentityId();
    String getApplyDisctObjName();
    String getApplyDisctObjType();
    Integer getApplyTabDpCondGrpId();
    String getApplyMemberAlias();
}
