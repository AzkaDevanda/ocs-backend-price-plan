package com.sts.sinorita.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListEventFeature {
    private Integer priority;
    private String sourceType;
    private String sourceTypeValue;
    private String desType;
    private String desTypeValue;
    private String labelShow;
}
