package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentItemRequestDto {
    private Integer seq;
    private Integer itemPercent;

    private Integer repeatTime;
    private Integer feePercent;
}
