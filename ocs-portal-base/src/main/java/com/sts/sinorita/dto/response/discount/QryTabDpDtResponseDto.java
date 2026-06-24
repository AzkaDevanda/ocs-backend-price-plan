package com.sts.sinorita.dto.response.discount;

import lombok.Data;

@Data
public class QryTabDpDtResponseDto {
    private Integer dpId;
    private Integer seqNo;
    private Character disctCalcMethod;
    private String sVal;
    private String eVal;
    private String refValue;
    private String refCellValue;
    private String refFloorValue;
    private Integer spId;
    private String showVal;
}
