package com.sts.sinorita.dto.response.accountconfig;

import lombok.Data;

@Data
public class ListIntalmentItemResponseDto {

  private Integer instalmentTypeId;
  private Integer seq;
  private Integer itemPercent;
  private Integer repeatTime;
  private Integer feePercent;

}
