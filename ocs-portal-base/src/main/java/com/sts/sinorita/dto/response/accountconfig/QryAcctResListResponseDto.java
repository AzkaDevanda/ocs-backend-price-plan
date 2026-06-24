package com.sts.sinorita.dto.response.accountconfig;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryAcctResListResponseDto {
    private Integer acctResId;
    private Integer parentAcctResId;
    private String acctResName;
    private String stdCode;
    private String isCurrency;
    private String balType;
    private Integer creditLimit;
    private String comments;
    private String refillable;
}
