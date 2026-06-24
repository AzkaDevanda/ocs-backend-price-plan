package com.sts.sinorita.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListAcmPriceDto {
    private Integer timeSpanUpId;
    private String timeSpanName;
    private Long effValue;
    private Long expValue;
    private Integer acctItemTypeId;
    private String accumulationType;
    private Character adjustMethod;
    private String price;
    private Long calculateUnit;
    private Integer timeSpanUpPriority;
}
