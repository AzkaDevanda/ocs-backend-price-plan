package com.ocs.portal.dto.response.priceplan;

import com.ocs.portal.dto.request.PricePlanTypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderPricePlanType {
    private String parentName;
    private List<PricePlanTypeDto> pricePlanTypeDto;
}
