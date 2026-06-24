package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsPlanOfferForRelaDto {
    private Integer offerId;
    private String offerName;
    private String networkTypeName;
}
