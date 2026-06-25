package com.ocs.portal.dto.response.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QryDataPrivByDataPrivCodeResponseDto {
  private Long dataPrivId;
  private String dataPrivName;
  private String comments;
  private String dataType;
  private String dataSrc;
  private Long spId;
  private String dataPrivCode;
}
