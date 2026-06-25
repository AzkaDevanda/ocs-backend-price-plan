package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSpanUpRequest {

  @Schema(description = "ID dari time span", example = "3")
  private Long timeSpanId;

  @Schema(description = "Unit kalkulasi (misalnya byte, KB, dll)", example = "1024")
  private Long calculationUnit;

  @Schema(description = "Metode perhitungan. Misalnya: F (Flat), P (Progresif)", example = "F")
  private String calculationMethod;

  @Schema(description = "Harga dalam string", example = "100000")
  private String price;

  private Long priority;

}
