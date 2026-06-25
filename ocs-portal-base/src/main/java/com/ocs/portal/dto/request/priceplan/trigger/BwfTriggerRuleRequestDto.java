package com.ocs.portal.dto.request.priceplan.trigger;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BwfTriggerRuleRequestDto {

    @Schema(example = "iiii18")
    private String sortRuleName;

    @Schema(example = "2025-08-13")
    private LocalDate effDate;

    @Schema(example = "2025-08-14")
    private LocalDate expDate;

    @Schema(example = "81g2i")
    private String comments;

    private List<BwfCondGroupDto> bwfCondGroupList;

    private List<BwfActionDto> bwfActionList;
//    @JsonProperty("BWF_SYS_ACTION_DTO")
    private BwfSysActionDto bwfSysActionDto;

    @Schema(example = "0")
    private int spId;

    @Schema(example = "null", nullable = true)
    private Integer nodeId;

    @Schema(example = "1003")
    private Integer triggerId;

    @Schema(example = "1")
    private Integer seq;
}
