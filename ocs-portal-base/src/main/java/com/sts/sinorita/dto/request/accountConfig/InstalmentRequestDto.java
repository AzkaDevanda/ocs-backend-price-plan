package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentRequestDto {
    private String instalmentTypeName;
    private List<AppliedAccountItemTypeRequestDto> appliedAccountItemType;
    private Integer firstPay;
    private Integer repeatTime;
    private Integer feePercent;
    private String comments;
    private List<InstalmentItemRequestDto> instalmentItems;
}
