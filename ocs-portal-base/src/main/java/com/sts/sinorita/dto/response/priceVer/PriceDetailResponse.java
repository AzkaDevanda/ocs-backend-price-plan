package com.sts.sinorita.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PriceDetailResponse {
    private Long priceId;
    private LocalDate effDate;
    private LocalDate expDate;
    private String priceName;
    private String value;
    private String payIndicator;
    private Integer rum;
    private Integer acctItemTypeId;
    private String remarks;
    private Integer reAttr;

}
