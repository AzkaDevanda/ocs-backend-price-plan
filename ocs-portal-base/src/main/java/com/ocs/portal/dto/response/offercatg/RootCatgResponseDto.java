package com.ocs.portal.dto.response.offercatg;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RootCatgResponseDto {
  private Integer offerCatgId;
  private String offerCatgName;
  private Integer seq;
  private String offerCatgCode;
  private LocalDateTime effDate;
  private LocalDateTime expDate;
  private String comments;
  private Integer offerCatgType;
  private String offerCatgClass;
  private String policyFlag;
}
