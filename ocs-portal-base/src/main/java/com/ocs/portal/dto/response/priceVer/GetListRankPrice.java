package com.ocs.portal.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListRankPrice {

    private Integer timeSpanUpId;
    private Integer rankUpId;
    private String timeSpanName;
    private Integer effValue;
    private Integer expValue;
    private Character adjustMethod;
    private String price;
    private Long calculateUnit;
    private Integer timeSpanUpPriority;

}
