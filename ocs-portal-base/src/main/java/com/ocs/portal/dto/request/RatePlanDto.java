package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RatePlanDto {
    @Schema(example = "3205")
    private Integer offerVerId;

    @Schema(example = "1")
    private Integer reId;

    @NotNull(message = "ratePlanName cannot be null")
    @Size(min = 1, max = 255, message = "ratePlanName must be between 1 and 255 characters")
    @Schema(example = "Testing")
    private String ratePlanName;

    @Schema(example = "null")
    private String ratePlanCode;

    @Size(max = 255, message = "ratePlanDesc must be less than 255 characters")
    @Schema(example = "null")
    private String remarks;

    @NotNull(message = "ratePlanType cannot be null")
    @Schema(example = "1")
    private Character ratePlanType;

    @Schema(example = "N")
    private Character templateFlag;

    @Schema(example = "null")
    private Integer catalogId;

    @Schema(example = "0")
    private Integer spId;

    private List<EventFeatureRequest> ratePlanZones;
}
