package com.sts.sinorita.dto.request.priceplan.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefValueDiscount {
    private String refValue;
    private String refCellValue;
    private String refFloorValue;

}
