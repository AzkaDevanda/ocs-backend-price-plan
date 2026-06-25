package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRankPriceDto {

    private Integer timeSpanId;
    private Integer offSet;
    private Integer duration;
    private Character adjustMethod;
    private String price;
    private Long calculateUnit;

}
