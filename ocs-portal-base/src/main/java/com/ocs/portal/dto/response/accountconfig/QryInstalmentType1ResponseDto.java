package com.ocs.portal.dto.response.accountconfig;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryInstalmentType1ResponseDto {
    private Long instalmentTypeId;
    private String instalmentTypeName;
    private Integer firstPay;
    private Long repeatTimes;
    private String comments;
    private Long feePercents;
}
