package com.ocs.portal.dto.response.priceplan;

import com.ocs.portal.dto.request.PricePlanTypeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuPriceplanTypeAccountReponse {
    private String accountHeader;
    private List<PricePlanTypeDto> list;
}
