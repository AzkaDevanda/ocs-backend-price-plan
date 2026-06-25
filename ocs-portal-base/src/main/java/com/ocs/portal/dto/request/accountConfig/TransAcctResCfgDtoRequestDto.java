package com.ocs.portal.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransAcctResCfgDtoRequestDto {
    private Long dayThreshold;

    private Long weekThreshold;

    private Long monthThreshold;

    private Long dayCount;

    private Long weekCount;

    private Long monthCount;

    private Long minResidualBal;

    private Long maxAllowed;

    private Long minAllowed;

    private Long transferFactor;
}
