package com.sts.sinorita.dto.request.priceplan.treshold;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SubsEventDto {

    @Schema(description = "ID dari event subscription", example = "77")
    private Integer subsEventId;

    @Schema(description = "Mode pemicu event", example = "1")
    private Character triggerMode;

    @Schema(description = "Status anti bill shock (Y/N)", example = "N")
    private Character antibillShock;

    @Schema(description = "Nama event", example = "(Gota) Quit Organization")
    private String eventName;

    @Schema(description = "ID parameter notifikasi (opsional)", example = "")
    private Integer notifyParamsId;

    @Schema(description = "Ekstensi atribut tambahan (opsional)", example = "")
    private String extAttr;

    @Schema(description = "Service Provider ID", example = "0")
    private Integer spId;

    @Schema(description = "ID event subscription sebelumnya (null jika tidak ada)", example = "null")
    private String oldSubsEventId;

    @Schema(description = "ID threshold ACM", example = "1202")
    private Integer acmThresholdId;

    @JsonIgnore
    public String stateSet;

}
