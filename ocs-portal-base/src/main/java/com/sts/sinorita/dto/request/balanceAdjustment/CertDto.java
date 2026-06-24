package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class CertDto {
  public Long certId;

  public Long certTypeId;

  public String certNbr;

  public String issueOrg;

  public Date issueDate;

  public Date effDate;

  public Date expDate;

  public String certAddress;

  public Long spId;

  private Long issueCountry;

  private String operationType;
}
