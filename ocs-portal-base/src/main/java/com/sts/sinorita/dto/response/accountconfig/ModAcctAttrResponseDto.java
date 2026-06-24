package com.sts.sinorita.dto.response.accountconfig;

import lombok.Data;

import java.util.List;

@Data
public class ModAcctAttrResponseDto {
    private List<AcctAttrSimpleDto> acctAttrArr;
    private List<AcctAttrFullDto> srcAcctAttrArr;
}
