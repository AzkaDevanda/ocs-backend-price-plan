package com.sts.sinorita.dto.response.offerapplycatg;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferApplyCatgResponseDto {
  private Integer offerId;
  private Integer catgId;
  private Integer spId;
  private Character excludeFlag;
  private String catgType;
  private String catgDefType;
  private String catgName;
  private String conditionName;
  private String comments;
  private LocalDateTime createdDate;
}
