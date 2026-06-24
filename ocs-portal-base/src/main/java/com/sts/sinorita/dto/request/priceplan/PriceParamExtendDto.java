package com.sts.sinorita.dto.request.priceplan;

import com.sts.sinorita.dto.request.TariffTemplateParamDto;
import lombok.Data;

@Data
public class PriceParamExtendDto extends TariffTemplateParamDto {
    private Long priceParamId;

    private Long priceId;
}
