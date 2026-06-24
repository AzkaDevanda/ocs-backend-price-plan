package com.sts.sinorita.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcmThresholdTrigerDto {
    private Integer triggerId;

    private String triggerBy; // "Threshold" atau "Ratio"
    private Integer interval; // Boleh null jika kosong
    private Character triggerPCRF; // Y atau N

    @Schema(example = "ruleId")
    private Integer ruleId; // Nama rule yang dipilih (id)

    @Schema(example = "10")
    @NotNull(message = "threshold cannot be null")
    @Size(min = 1, max = 255, message = "threshold must be between 0 to 9 number")
    private Integer threshold; // Wajib diisi, contoh: 10

    @Schema(example = "1")
    @NotNull(message = "feature cannot be null")
    private Integer feature; // Nama feature yang dipilih / Re_attr id

    @Schema(example = "Terminal, Cros, Precise")
    private String triggerMode; // Mode trigger yang dipilih
    private String thresholdAttribute; // Atribut threshold yang diisi
    private Integer billshockRuleId;

}
