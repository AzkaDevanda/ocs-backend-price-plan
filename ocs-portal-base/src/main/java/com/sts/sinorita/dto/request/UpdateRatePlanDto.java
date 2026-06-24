package com.sts.sinorita.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRatePlanDto {
    @NotNull
    private String ratePlanName;
    private String ratePlanCode;
    private String remarks;
    private Character zoneFlag;

    private List<EventFeatureRequest> ratePlanZones;

}
