package com.sts.sinorita.dto.request.accountConfig;

import com.sts.sinorita.dto.response.accountconfig.AcctAttrFullDto;
import com.sts.sinorita.dto.response.accountconfig.AcctAttrSimpleDto;
import com.sts.sinorita.dto.response.accountconfig.AcctValuesListDto;
import lombok.Data;

import java.util.List;
@Data
public class ModAcctAttrRequestDto {
    private Integer acctId;
    private Integer spId;
    private List<AcctValuesListDto> acctValuesListDto;


}
