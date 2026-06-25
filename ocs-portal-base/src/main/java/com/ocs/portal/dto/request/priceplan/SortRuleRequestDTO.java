package com.ocs.portal.dto.request.priceplan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SortRuleRequestDTO {

    @Schema(description = "Nama aturan urutan", example = "step 1")
    private String sortRuleName;

    @Schema(description = "Tanggal dan waktu berlaku", example = "2025-07-08 13:32:41")
    private LocalDate effDate;

    @Schema(description = "Tanggal dan waktu berlaku", example = "2025-07-08 13:32:41")
    private LocalDate expDate;

    @Schema(description = "Daftar grup kondisi BWF", example = "null")
    private List<BwfGroup> bwfCondGroupList;

    @Schema(description = "Objek aksi sistem BWF")
    private BwfSysActionDto bwfSysActionDto;

    @Schema(description = "ID Service Provider", example = "0")
    private Integer spId;

    @Schema(description = "ID Node", example = "null")
    private Integer nodeId;

    @Schema(description = "ID Trigger", example = "801")
    private Integer triggerId;

    @Schema(description = "Urutan eksekusi", example = "1")
    private int seq;

    private String comments;

    public static class BwfSysActionDto {

        @Schema(description = "Nama aksi sistem", example = "")
        private String sysActionName;

        @Schema(description = "ID Service Provider", example = "0")
        private int spId;
    }

}
