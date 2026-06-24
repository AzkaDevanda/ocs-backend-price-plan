package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TriggerTypeResponseDto {
    private Character triggerModeId;
    private String triggerMode;
}
