package com.ocs.portal.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceResponseDto {
    private Integer accumulationTypeId;
    private String accumulationType;
}
