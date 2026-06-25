package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcmCalcDto {

    // resource id
    @Schema(example = "1")
    private Integer acctItemTypeId;

//    @Schema(example = "A")
//    private Character State;

    @Schema(example = "1024")
    private Long calculateUnit;

    private Integer timeSpanUpId;

//    @Schema(example = "1")
//    private Integer staffId;
}
