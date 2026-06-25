package com.ocs.portal.dto.request.priceplan.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabDpCondGrpDtListRequestDto {
    private Integer grpId;
    private Integer seqNo;
    private Integer lDpRefCondId;
    private String lParam1;
    private Character sortOperator;
    private String rVal;
}
