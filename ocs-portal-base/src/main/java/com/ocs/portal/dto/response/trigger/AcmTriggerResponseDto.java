package com.ocs.portal.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AcmTriggerResponseDto {

    private Integer triggerId;
    private LocalDateTime effDate;
    private LocalDateTime expDate;
    private ResourceResponseDto accumulationType;
    private TriggerTypeResponseDto triggerMode;
    private String state;
    private LocalDateTime stateDate;
    private Character destination;
}
