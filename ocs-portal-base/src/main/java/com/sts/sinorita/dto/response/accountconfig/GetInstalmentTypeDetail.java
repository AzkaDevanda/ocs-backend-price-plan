package com.sts.sinorita.dto.response.accountconfig;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class GetInstalmentTypeDetail {
  private Integer instalmentTypeId;
  private String instalmentTypeName;
  private List<QryInstalmentTypeItemByInstalmentTypeIdResponseDto> appliedAccountItemType;
  private Integer firstPay;
  private Character state;
  private Integer totalPhase;
  private LocalDateTime stateDate;
  private String comments;
  private Integer spId;
  private Integer feePercent;

  List<ListIntalmentItemResponseDto> instalmentItems;

}
