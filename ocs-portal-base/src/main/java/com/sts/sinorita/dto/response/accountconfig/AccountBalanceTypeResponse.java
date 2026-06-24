package com.sts.sinorita.dto.response.accountconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceTypeResponse {
    private Long acctResId;
    private String balanceTypeName;
    private Character isCurrency;
    private String balCatalogName;
    private String defaultAcctItemTypeName;
    private BigDecimal maxValue;
    private String unitTypename;
    private Integer priority;
    private Integer maxExpDate;
    private Character resetZero;
    private String acmTypeName;
    private Character acmUnit;
    private BigDecimal floorLimit;
    private BigDecimal dailyCeilLimit;
    private BigDecimal dailyFloorLimit;
    private Character isOverdraft;
}
