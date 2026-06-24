package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbEventBenefitDto {
  public Long eventInstId;

  public Long acctBookId;

  public Long priceId;

  public Long subBalTypeId;

  public LocalDateTime effDate;

  public LocalDateTime expDate;

  public Long benefitBal;

  public Long spId;
}
