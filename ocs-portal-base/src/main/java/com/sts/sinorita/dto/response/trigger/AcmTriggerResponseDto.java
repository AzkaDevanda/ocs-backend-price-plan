package com.sts.sinorita.dto.response.trigger;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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
