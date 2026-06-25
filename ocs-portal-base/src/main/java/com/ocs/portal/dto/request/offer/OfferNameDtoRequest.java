package com.ocs.portal.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferNameDtoRequest {
    private Integer offerId;
    private String offerName;
    private String offerCode;
    private Character applyLevel;
    private Long offerCatgId;
}
