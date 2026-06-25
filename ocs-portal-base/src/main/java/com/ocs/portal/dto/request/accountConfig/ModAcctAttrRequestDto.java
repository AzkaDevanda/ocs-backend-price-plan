package com.ocs.portal.dto.request.accountConfig;

import com.ocs.portal.dto.response.accountconfig.AcctValuesListDto;
import lombok.Data;

import java.util.List;
@Data
public class ModAcctAttrRequestDto {
    private Integer acctId;
    private Integer spId;
    private List<AcctValuesListDto> acctValuesListDto;


}
