package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnceFeeDto {
  public Long acctResId;

  public Long charge;

  public Long priceId;

  public String refAttr;
}
