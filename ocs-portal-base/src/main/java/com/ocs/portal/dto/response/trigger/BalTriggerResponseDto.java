package com.ocs.portal.dto.response.trigger;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalTriggerResponseDto {
    private Integer triggerId;
    private Integer offerVerId;
    private Integer offerId;
    private String offerName;
    private Integer acctResId;
    private String acctResIdList;
    private Character triggerType;
    private String triggerTypeName;
    private LocalDate effDate;
    private Character state;
    private LocalDate stateDate;
    private LocalDate expDate;
    private Character isLimitBalance;
    private String isLimit;
    private String destination;
}
