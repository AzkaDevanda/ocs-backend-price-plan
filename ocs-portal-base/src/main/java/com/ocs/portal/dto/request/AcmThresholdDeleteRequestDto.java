package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcmThresholdDeleteRequestDto {
    private Long acmThresholdId;
    private String touchPcrf;
}
