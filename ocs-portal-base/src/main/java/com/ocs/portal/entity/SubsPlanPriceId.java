package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubsPlanPriceId {
    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer offerVerId;

    @Column(name = "PRICE_TYPE", nullable = false, length = 1)
    private String priceType;
}
