package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePlanForRelaDto{
    private Integer offerId;
    private String offerName;
    private Character networkType;
    private String networkTypeName;
}
