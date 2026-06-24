package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependOfferForRelaDto {
    private Integer offerId;
    private String offerName;
    private String servType;
    private String networkType;
    private String networkTypeName;
}
