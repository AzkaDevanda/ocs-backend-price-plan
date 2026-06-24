package com.sts.sinorita.dto.response.discount;

import lombok.Data;

@Data
public class QryTabDpInfoDto{
    private Integer dpId;
    private String tabDpType;
    private Integer refDisctObjId;
    private Integer acctItemTypeId;
    private String distributeMethod;
    private Integer tabDpCondGrpId;
    private Integer calcDisctObjId;
    private Integer applyDisctObjId;
    private String negativeFlag;
    private Integer spId;

    private String acctItemTypeName;
    private String acctItemTypeCode;
    private Integer acctResId;

    private Integer refObjIdentityId;
    private String refDisctObjName;
    private String refDisctObjType;
    private Integer refTabDpCondGrpId;
    private Integer refGstSeq;
    private String refMemberAlias;

    private Integer calcObjIdentityId;
    private String calcDisctObjName;
    private String calcDisctObjType;
    private Integer calcTabDpCondGrpId;
    private Integer calcGstSeq;
    private String calcMemberAlias;

    private Integer applyObjIdentityId;
    private String applyDisctObjName;
    private String applyDisctObjType;
    private Integer applyTabDpCondGrpId;
    private String applyMemberAlias;
}
