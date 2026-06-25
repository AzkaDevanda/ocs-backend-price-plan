package com.ocs.portal.dto.response.priceplan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceplanMenu {
    private List<HeaderPricePlanType> subscription;
    private MenuPriceplanTypeAccountReponse accountReponse;
}
