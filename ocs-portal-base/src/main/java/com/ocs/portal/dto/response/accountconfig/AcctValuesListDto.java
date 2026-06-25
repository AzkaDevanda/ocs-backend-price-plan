package com.ocs.portal.dto.response.accountconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctValuesListDto {
    private Integer attrValueId;
    private Integer baseAttrId;
    private String valueMark;
    private String value;
    private String attrName;

}
