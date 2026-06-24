package com.sts.sinorita.dto.response.offergroup;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferGroupAndMemberResponseDto {
  private Long offerGroupId;
  private String offerGroupName;
  private String offerGroupCode;
  private String offerGroupType;
  private String groupType;
  private Integer upperLimit;
  private Integer lowerLimit;
  private LocalDate effDate;
  private LocalDate expDate;
  private LocalDate createdDate;
  private String state;
  private LocalDate stateDate;
  private String shareFlag;
  private Long indepProdSpecId;
  private Long offerVerId;
  private String comments;
  private String networkType;
}
