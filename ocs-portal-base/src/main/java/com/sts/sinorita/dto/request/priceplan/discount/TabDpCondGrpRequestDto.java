package com.sts.sinorita.dto.request.priceplan.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabDpCondGrpRequestDto {
    List<TabDpCondGrpDtListRequestDto> DiscountCondition;
    private Character dpRefCondType;
    private String tabDpCondGrpName;
}
