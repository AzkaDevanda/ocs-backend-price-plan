package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcmUpDto {

    @Schema(example = "null")
    private Integer timeSpanUpId;

    @Schema(example = "1024")
    private Long calculateUnit;

    @Schema(example = "F")
    private Character adjustMethod;

    // resource Id
    @Schema(example = "1")
    private Integer acctItemTypeId;

    @Schema(example = "100000")
    private String price;

    @Schema(example = "0")
    private Long rangeEffVal;

    @Schema(example = "0")
    private Long rangeExpVal;

}