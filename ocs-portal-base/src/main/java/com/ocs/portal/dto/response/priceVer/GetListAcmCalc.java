package com.ocs.portal.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListAcmCalc {

    private Integer acctItemTypeId;
    private String accumulationType;
    private Long calculateUnit;
    private Integer timeSpanUpId;
    private String timeSpanName;
    private Integer timeSpanUpPriority;

}
