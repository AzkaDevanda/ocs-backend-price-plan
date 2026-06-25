package com.ocs.portal.dto.request.accountConfig;

import lombok.Data;

@Data
public class AddDDParamDto {
    private Integer paymentMethodId;
    private Integer daysBefExtra;
    private String spIban;
    private Integer reIssueDelay;
    private Integer closeMandateLimit;
}
