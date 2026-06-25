package com.ocs.portal.dto.request;

import com.ocs.portal.dto.request.priceplan.RefValueExtendDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModRePricePlanDto {
    private Integer offerVerId;
    private Integer reId;
    private String ruleComments;
    private String ruleScript;
    private String scriptPage;
    private Long scriptTempletId;
    private String funPrefix;
    private List<RefValueExtendDto> inputRefValueBoField;
    private Long pythonRefValue;
    private Integer spId;
}
