package com.sts.sinorita.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListTimeSpanPrice {

    private Integer timeSpanUpId;
    private Integer priority;
    private String timeSpanUpName;
    private Character calculationMethod;
    private String price;
    private Long calculateUnit;

}
