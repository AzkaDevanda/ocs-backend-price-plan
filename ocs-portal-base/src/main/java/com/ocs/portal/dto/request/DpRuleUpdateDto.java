package com.ocs.portal.dto.request;

import com.ocs.portal.dto.request.priceplan.discount.DpRuleRequestDto;
import lombok.Data;

@Data
public class DpRuleUpdateDto {
    private Integer spId;
    private Integer accItemTypeId;
    private DpRuleRequestDto dpRuleRequestDto;
}
