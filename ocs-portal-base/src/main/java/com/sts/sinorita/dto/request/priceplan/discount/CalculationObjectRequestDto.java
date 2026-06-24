package com.sts.sinorita.dto.request.priceplan.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationObjectRequestDto {
    private Integer objIdentityId;
    private Integer gstSeq;
    private String objectName;
    private Character objectType;
    private String memberAlias;
}
