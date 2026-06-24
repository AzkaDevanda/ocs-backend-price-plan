package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageEventRequestDto {
    @Schema(description = "Daftar Resource Event ID", example = "[501, 1, 4]")
    private List<Integer> reId;
}
