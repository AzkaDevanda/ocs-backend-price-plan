package com.ocs.portal.dto.request.priceplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceParamDto {
    public Long priceParamId;

    public Long priceId;

    public String paramType;

    public Long simpleParamId;

    public Long tableParamId;

    public Long spId;
}
