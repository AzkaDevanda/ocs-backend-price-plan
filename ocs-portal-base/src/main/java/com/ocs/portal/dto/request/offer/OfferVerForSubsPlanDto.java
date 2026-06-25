package com.ocs.portal.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferVerForSubsPlanDto {
    private Integer id;
    private Integer seq;
    private LocalDate effDate;
    private Integer offerId;
}
