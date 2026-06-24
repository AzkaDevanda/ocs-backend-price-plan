package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePriceRequest {

    private Long priceId;
    private Character reType;
    boolean isDepositPrice;
    private Integer priceVerId;

}
