package com.sts.sinorita.dto.request;

import com.sts.sinorita.dto.request.priceplan.discount.DiscountRequestDto;
import com.sts.sinorita.dto.request.priceplan.discount.DpRuleRequestDto;
import lombok.Data;

@Data
public class DpRuleUpdateDto {
    private Integer spId;
    private Integer accItemTypeId;
    private DpRuleRequestDto dpRuleRequestDto;
}
