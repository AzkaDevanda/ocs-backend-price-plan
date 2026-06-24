package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class ProdAttrValueResponseDto {
  private Long prodId;

  private Long attrId;

  private String value;

  private Date effDate;

  private Date expDate;

  private Date updateDate;

  private String needUpload;

  private Long spId;

  private Long routingId;

  private String uploadType;
}
