package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertUsageEventRequest {
    @Schema(description = "ID dari offer version", example = "99")
    private Integer offerVerId;
    private List<Integer> reId;
}
