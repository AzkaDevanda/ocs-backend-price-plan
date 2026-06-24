package com.sts.sinorita.dto.request.priceplan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BwfCond {
    @Schema(description = "Urutan kondisi", example = "1")
    private Integer seq;
    private Integer condGroupId;
    private Integer reAttr;

    @Schema(description = "Operator sort (kode)", example = "8")
    private String sortOperator;
    private boolean isConsist;

    @Schema(description = "Nama atribut awal", example = "3GPP_RAT_TYPE")
    private String reAttrName;

    @Schema(description = "Nama operator sort", example = "<=")
    private String sortOperatorName;

    @Schema(description = "Apakah konstanta (Y/N)", example = "N")
    private String isConst;

    @Schema(description = "Atribut referensi (kanan)", example = "1201")
    private String rReAttr;

    @Schema(description = "Nama atribut referensi (kanan)", example = "ACCT_CHANGE_STAGE")
    private String rReAttrName;

    @Schema(description = "Service Provider ID", example = "0")
    private Integer spId;

}
