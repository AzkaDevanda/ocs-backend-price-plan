package com.ocs.portal.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferGroupForRelaDto {
    private Integer offerId;
    private String offerName;
    private String indOfferName;
    private String subsPlanName;
    private LocalDate effDate;
    private LocalDate expDate;
    private String networkTypeName;
}
