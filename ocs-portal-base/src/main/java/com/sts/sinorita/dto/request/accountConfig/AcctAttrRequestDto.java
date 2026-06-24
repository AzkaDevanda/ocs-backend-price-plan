package com.sts.sinorita.dto.request.accountConfig;

import lombok.Data;

import java.util.List;

@Data
public class AcctAttrRequestDto {
    private Integer spId;
    private List<AcctAttrItemDto> acctAttrRequestDtos;
    private List<AcctAttrItemDto> srcAcctAttr;

}
