package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctBalanceTypeListDto {
    private Integer acctResId;
    private String acctResName;
    private String isCurrency;
}
