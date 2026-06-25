package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {
  public Long acctResId;

  public Long charge;

  public Long prolongDays;

  public Long priceId;

  public Date expDate;

  public Date effDate;

  public String extendMode;
}
