package com.sts.sinorita.dto.request.common;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PagingRequestDto {

  @Parameter(description = "Search keyword")
  @Schema(example = "")
  private String search;

  @Parameter(description = "Page number (starts from 1)")
  @Schema(example = "1")
  private Integer page;

  @Parameter(description = "Number of records per page")
  @Schema(example = "10")
  private Integer size;

  @Parameter(description = "Sort field (e.g., id, name)")
  @Schema(example = "id")
  private String sortBy;

  @Parameter(description = "Sort direction (asc or desc)")
  @Schema(example = "asc")
  private String sortDirection;
}
