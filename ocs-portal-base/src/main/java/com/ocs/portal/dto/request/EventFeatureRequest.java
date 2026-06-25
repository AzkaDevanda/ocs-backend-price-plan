package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFeatureRequest {
    //    private Integer ratePlanId;
    private Integer priority;
    private Character mappingSrcType;
    private String mappingSrcValue;
    private Character mappingDesType;
    private String mappingDesValue;
    private String labelShow;

}
