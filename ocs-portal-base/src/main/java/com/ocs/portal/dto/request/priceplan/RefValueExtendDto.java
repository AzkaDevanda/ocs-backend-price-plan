package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

@Data
public class RefValueExtendDto extends  RefValueDto {
    private String paramName;
    RefValueFormulaDto Formula;
    private String pythonParamName;
}
