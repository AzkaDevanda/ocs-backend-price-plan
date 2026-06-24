package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class IndividualLifeCycleDto {
  public String objProdState;

  public Long prodStateAddDays;

  public Long subsId;

  public Date oldD2EExpDate;
}
