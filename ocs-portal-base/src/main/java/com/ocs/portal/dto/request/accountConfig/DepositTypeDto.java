package com.ocs.portal.dto.request.accountConfig;

import lombok.Data;

@Data
public class DepositTypeDto {
    private Integer depositTypeId;

    private String name;

    private String comments;

    private Integer charge;

    private Integer spId;

    private String depositTypeCode;

    private Character refundable;

    private Character transCredit;

    private Integer checkDuration;
}
