package com.ocs.portal.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferCatgMemDto {
        private Integer seq;
        private Integer offerCatgMemId;
        private Integer offerId;
        private Character offerType;
        private String offerName;
        private String offerCode;
        private LocalDate effDate;
        private LocalDate expDate;
        private Character duplicateFlag;
        private Integer expOff;
        private Character expTimeUnit;
        private Character isPackage;
        private Character applyLevel;
        private Character policyFlag;
        private Character warnLevel;
        private Character pricePlanType;

}
