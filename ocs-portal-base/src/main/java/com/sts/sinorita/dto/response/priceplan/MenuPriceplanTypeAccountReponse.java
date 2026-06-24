package com.sts.sinorita.dto.response.priceplan;

import com.sts.sinorita.dto.request.PricePlanTypeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuPriceplanTypeAccountReponse {
    private String accountHeader;
    private List<PricePlanTypeDto> list;
}
