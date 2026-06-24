package com.sts.sinorita.dto.response.offer;

import lombok.Data;

@Data
public class GetListOfferCatgResponse {
    private Integer offerCatgId;
    private String offerCatgName;
    private Integer offerCount;
}
