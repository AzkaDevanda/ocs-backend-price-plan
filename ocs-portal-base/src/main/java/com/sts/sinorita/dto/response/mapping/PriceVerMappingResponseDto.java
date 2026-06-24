package com.sts.sinorita.dto.response.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceVerMappingResponseDto {

    private int priceVerId;
    private String effDate;
    private String expDate;
    private List<PriceMappingResponse> price;

}
