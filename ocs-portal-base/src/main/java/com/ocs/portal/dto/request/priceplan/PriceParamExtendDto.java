package com.ocs.portal.dto.request.priceplan;

import com.ocs.portal.dto.request.TariffTemplateParamDto;
import lombok.Data;

@Data
public class PriceParamExtendDto extends TariffTemplateParamDto {
    private Long priceParamId;

    private Long priceId;
}
